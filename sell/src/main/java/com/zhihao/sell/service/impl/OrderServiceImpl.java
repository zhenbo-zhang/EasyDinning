package com.zhihao.sell.service.impl;

import com.zhihao.sell.converter.OrderMaster2OrderDTOConverter;
import com.zhihao.sell.dataobject.OrderDetail;
import com.zhihao.sell.dataobject.OrderMaster;
import com.zhihao.sell.dataobject.ProductInfo;
import com.zhihao.sell.dto.CartDTO;
import com.zhihao.sell.dto.OrderDTO;
import com.zhihao.sell.enums.OrderStatusEnum;
import com.zhihao.sell.enums.PayStatusEnum;
import com.zhihao.sell.enums.ResultEnum;
import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.repository.OrderDetailRepository;
import com.zhihao.sell.repository.OrderMasterRepository;
import com.zhihao.sell.service.OrderService;
import com.zhihao.sell.service.ProductService;
import com.zhihao.sell.service.WebSocket;
import com.zhihao.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of OrderService.
 */
// controller -> service -> DAO
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  private ProductService productService;

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Autowired
  private OrderMasterRepository orderMasterRepository;

  @Autowired
  private WebSocket webSocket;

  /**
   * Create a new order. Data is given by controller.
   */
  @Override
  // Roll back if there is exception.
  @Transactional
  // orderDTO is given by controller
  public OrderDTO create(OrderDTO orderDTO) {

    String orderId = KeyUtil.genUniqueKey();
    BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

    // 1. check product(Amount, price)
    // By now, orderDetail is given by front end, only contains id and amount
    for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
      ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
      if (productInfo == null) {
        throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
      }

      // 2. calculate the amount
      orderAmount = productInfo.getProductPrice()
          .multiply(new BigDecimal(orderDetail.getProductQuantity()))
          .add(orderAmount);

      // 3. save order detail
      orderDetail.setDetailId(KeyUtil.genUniqueKey());
      orderDetail.setOrderId(orderId);
      // orderDetail only contains id and amount, we need to copy name .. to it
      BeanUtils.copyProperties(productInfo, orderDetail);
      orderDetailRepository.save(orderDetail);
    }

    // 4. save data into database（orderMaster, orderDetail）
    // OrderMaster doesn't contains order details information in db.
    OrderMaster orderMaster = new OrderMaster();
    orderDTO.setOrderId(orderId);
    BeanUtils.copyProperties(orderDTO, orderMaster);
    // These fields have be overshadowed by the above method.
    orderMaster.setOrderAmount(orderAmount);
    orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
    orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
    orderMasterRepository.save(orderMaster);

    // 5. decrease stock
    List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
        new CartDTO(e.getProductId(), e.getProductQuantity())
    ).collect(Collectors.toList());
    productService.decreaseStock(cartDTOList);

    // 6. send web socket message when a new order is created
    webSocket.sendMessage(orderDTO.getOrderId());

    return orderDTO;
  }

  /**
   * Find a order base on an order id.
   *
   * @param orderId - the order id to check
   * @return a order base on an order id
   */
  @Override
  public OrderDTO findOne(String orderId) {
    // Check whether the order exists or not.
    OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
    if (orderMaster == null) {
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
    if (CollectionUtils.isEmpty(orderDetailList)) {
      throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
    }
    OrderDTO orderDTO = new OrderDTO();
    BeanUtils.copyProperties(orderMaster, orderDTO);
    orderDTO.setOrderDetailList(orderDetailList);
    return orderDTO;
  }

  /**
   * Return the list of order.
   *
   * @param buyerOpenid - the open id of the user
   * @param pageable - the Pageable object
   * @return the list of order
   */
  @Override
  public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
    Page<OrderMaster> orderMasterPage = orderMasterRepository
        .findByBuyerOpenid(buyerOpenid, pageable);
    List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter
        .convert(orderMasterPage.getContent());
    return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
  }

  /**
   * Cancel the order.
   *
   * @param orderDTO - the order to cancel
   * @return the order DTO
   */
  @Override
  @Transactional
  public OrderDTO cancel(OrderDTO orderDTO) {
    OrderMaster orderMaster = new OrderMaster();

    // check order status (Only new orders can be canceled)
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("【Cancel Order】Wrong Order Status，orderId={}, orderStatus={}",
          orderDTO.getOrderId(), orderDTO.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    // change order status. If we want to manipulate db, must OrderDTO -> OrderMaster
    orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster updateResult = orderMasterRepository.save(orderMaster);
    if (updateResult == null) {
      log.error("【Cancel Order】Update Failure, orderMaster={}", orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    // check stock
    if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
      log.error("【Cancel Order】No Order Detail Available, orderDTO={}", orderDTO);
      throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
    }
    // convert every orderDetail to cartDTO, in order to increase stock
    List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
        .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
        .collect(Collectors.toList());
    productService.increaseStock(cartDTOList);
    // return the order DTO that has been taken care of
    return orderDTO;
  }

  /**
   * Finish the order.
   *
   * @param orderDTO - the finished order
   * @return the DTO of the finished order
   */
  @Override
  @Transactional
  public OrderDTO finish(OrderDTO orderDTO) {
    // check order status (only new order can be finished)
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("【Finish Order】Wrong Order Status, orderId={}, orderStatus={}",
          orderDTO.getOrderId(), orderDTO.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    // change status
    orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster updateResult = orderMasterRepository.save(orderMaster);
    if (updateResult == null) {
      log.error("【Finish Order】Update Failure, orderMaster={}", orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    return orderDTO;
  }

  /**
   * Pay for the order.
   *
   * @param orderDTO - the order to pay
   * @return the paid order
   */
  @Override
  @Transactional
  public OrderDTO paid(OrderDTO orderDTO) {
    // check order status
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("【Order Payment】Wrong Payment Failure, orderId={}, orderStatus={}",
          orderDTO.getOrderId(), orderDTO.getOrderStatus());
      throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
    }

    // check pay status
    if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
      log.error("【Order Payment】Wrong Payment Status, orderDTO={}", orderDTO);
      throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
    }

    // update pay status
    orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
    OrderMaster orderMaster = new OrderMaster();
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster updateResult = orderMasterRepository.save(orderMaster);
    if (updateResult == null) {
      log.error("【Order Payment】Update Failure, orderMaster={}", orderMaster);
      throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
    }

    return orderDTO;
  }

  /**
   * Return all the products (Used by seller).
   *
   * @param pageable - a Pageable object
   * @return all the products (Used by seller)
   */
  @Override
  public Page<OrderDTO> findList(Pageable pageable) {
    Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
    List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter
        .convert(orderMasterPage.getContent());
    return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());

  }
}
