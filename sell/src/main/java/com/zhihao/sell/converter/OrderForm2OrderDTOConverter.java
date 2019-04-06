package com.zhihao.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihao.sell.dataobject.OrderDetail;
import com.zhihao.sell.dto.OrderDTO;
import com.zhihao.sell.enums.ResultEnum;
import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert an order form to order DTO
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

  public static OrderDTO convert(OrderForm orderForm) {

    // a json handler (convert a json encode as a string to data structure we want)
    Gson gson = new Gson();

    OrderDTO orderDTO = new OrderDTO();

    orderDTO.setBuyerName(orderForm.getName());
    orderDTO.setBuyerPhone(orderForm.getPhone());
    orderDTO.setBuyerAddress(orderForm.getAddress());
    orderDTO.setBuyerOpenid(orderForm.getOpenid());

    List<OrderDetail> orderDetailList = new ArrayList<>();
    try {
      orderDetailList = gson.fromJson(orderForm.getItems(),
          new TypeToken<List<OrderDetail>>() {
          }.getType());
    } catch (Exception e) {
      log.error("【Object Transform】Unexpected Error, string={}", orderForm.getItems());
      throw new SellException(ResultEnum.PARAM_ERROR);
    }

    orderDTO.setOrderDetailList(orderDetailList);

    return orderDTO;

  }
}
