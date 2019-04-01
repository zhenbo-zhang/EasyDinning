package com.zhihao.sell.service;

import com.imooc.sell.dto.OrderDTO;
import com.zhihao.sell.dto.OrderDTO;

public interface BuyerService {

    // Check an Order
    OrderDTO findOrderOne(String openid, String orderId);

    // Cancel an Order
    OrderDTO cancelOrder(String openid, String orderId);
}
