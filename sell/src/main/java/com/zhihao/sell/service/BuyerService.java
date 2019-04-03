package com.zhihao.sell.service;

import com.zhihao.sell.dto.OrderDTO;

/**
 * Interface BuyerService used to find or cancel an order.
 */
public interface BuyerService {

  // Check an Order
  OrderDTO findOrderOne(String openid, String orderId);

  // Cancel an Order
  OrderDTO cancelOrder(String openid, String orderId);
}
