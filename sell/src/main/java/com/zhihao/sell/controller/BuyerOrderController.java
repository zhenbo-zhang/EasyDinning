package com.zhihao.sell.controller;


import com.zhihao.sell.VO.ResultVO;
import com.zhihao.sell.converter.OrderForm2OrderDTOConverter;
import com.zhihao.sell.dto.OrderDTO;
import com.zhihao.sell.enums.ResultEnum;
import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.form.OrderForm;
import com.zhihao.sell.service.BuyerService;
import com.zhihao.sell.service.OrderService;
import com.zhihao.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private BuyerService buyerService;

  //Create Order
  @PostMapping("/create")
  public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
      BindingResult bindingResult) {
    // check if there are errors after validation
    if (bindingResult.hasErrors()) {
      log.error("【Create Order】Wrong parameter, orderForm={}", orderForm);
      throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }

    // convert the orderForm to orderDTO
    OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
    if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
      log.error("【Create Order】Shopping Cart cannot be empty");
      throw new SellException(ResultEnum.CART_EMPTY);

    }
    OrderDTO createResult = orderService.create(orderDTO);
    Map<String, String> map = new HashMap<>();
    map.put("orderId", createResult.getOrderId());

    return ResultVOUtil.success(map);
  }

  // Order List
  @GetMapping("/list")
  public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {

    if (StringUtils.isEmpty(openid)) {
      log.error("【Check Order List】openId is empty");
      throw new SellException(ResultEnum.PARAM_ERROR);
    }
    PageRequest request = new PageRequest(page, size);
    Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

    return ResultVOUtil.success(orderDTOPage.getContent());

  }

  //Order Detail
  @GetMapping("/detail")
  public ResultVO<Order> detail(@RequestParam("openid") String openid,
      @RequestParam("orderId") String orderId) {
    OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
    return ResultVOUtil.success(orderDTO);
  }

  //Cancel Order
  @PostMapping("/cancel")
  public ResultVO cancel(@RequestParam("openid") String openid,
      @RequestParam("orderId") String orderId) {
    buyerService.cancelOrder(openid, orderId);
    return ResultVOUtil.success();
  }
}
