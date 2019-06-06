package com.zhihao.sell.service.impl;

import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.dto.OrderDTO;
import com.zhihao.sell.enums.ResultEnum;
import com.zhihao.sell.service.BuyerService;
import com.zhihao.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Class BuyerService used to find or cancel an order.
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "buyer")
public class BuyerServiceImpl implements BuyerService {

  @Autowired
  private OrderService orderService;

  /**
   * Find the order with the openid and order id.
   *
   * @param openid - the open id
   * @param orderId - the order id
   * @return the order with the openid and order id
   */
  @Override
  @Cacheable(key = "#orderId")
  public OrderDTO findOrderOne(String openid, String orderId) {
    return checkOrderOwner(openid, orderId);
  }

  /**
   * Cancel the order with the openid and order id.
   *
   * @param openid - the open id
   * @param orderId - the order id
   * @return the canceled order with the openid and order id
   */
  @Override
  public OrderDTO cancelOrder(String openid, String orderId) {
    OrderDTO orderDTO = checkOrderOwner(openid, orderId);
    if (orderDTO == null) {
      log.error("【Cancel Order】Cannot Find the Order，orderId={}", orderId);
      throw new SellException(ResultEnum.ORDER_NOT_EXIST);
    }
    return orderService.cancel(orderDTO);
  }

  /**
   * Helper method used by the above methods.
   *
   * @param openid - the open id
   * @param orderId - the order id
   * @return the order with the openid and order id
   */
  private OrderDTO checkOrderOwner(String openid, String orderId) {
    OrderDTO orderDTO = orderService.findOne(orderId);
    if (orderDTO == null) {
      return null;
    }
    if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
      log.error("【Check Order】Wrong Open Id. openid={}, orderDTO={}", openid, orderDTO);
      throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
    }
    return orderDTO;
  }
}
