package com.zhihao.sell.service;

import com.imooc.sell.dto.OrderDTO;
import com.zhihao.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDTO create(OrderDTO orderMaster);

    OrderDTO findOne(String orderId);

    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    OrderDTO cancel(OrderDTO orderDTO);

    OrderDTO finish(OrderDTO orderDTO);

    OrderDTO paid(OrderDTO orderDTO);

    Page<OrderDTO> findList(Pageable pageable);
}
