package com.zhihao.sell.converter;

import com.zhihao.sell.dataobject.OrderMaster;
import com.zhihao.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

// Util Class that convert a OrderMaster to OrderDTO.

public class OrderMaster2OrderDTOConverter {

  public static OrderDTO convert(OrderMaster orderMaster) {
    OrderDTO orderDTO = new OrderDTO();
    BeanUtils.copyProperties(orderMaster, orderDTO);
    return orderDTO;
  }

  public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
    return orderMasterList.stream()
        .map(e -> convert(e)).collect(Collectors.toList());
  }
}
