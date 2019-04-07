package com.zhihao.sell.service;

import com.zhihao.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface OrderService.
 */
public interface OrderService {

  // Create new order.
  OrderDTO create(OrderDTO orderMaster);

  // Find one order.
  OrderDTO findOne(String orderId);

  // Find list of orders with the open id of the buyer.
  Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

  // Cancel the order.
  OrderDTO cancel(OrderDTO orderDTO);

  // Finish the order.
  OrderDTO finish(OrderDTO orderDTO);

  // Pay for the order.
  OrderDTO paid(OrderDTO orderDTO);

  // Find list of orders.
  Page<OrderDTO> findList(Pageable pageable);
}
