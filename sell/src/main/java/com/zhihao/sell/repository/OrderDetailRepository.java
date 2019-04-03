package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface OrderDetailRepository.
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

  /**
   * Return all of details of an order.
   *
   * @param orderId - the id of the order
   * @return all of details of an order
   */
  List<OrderDetail> findByOrderId(String orderId);
}
