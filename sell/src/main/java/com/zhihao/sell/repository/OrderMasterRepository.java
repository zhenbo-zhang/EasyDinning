package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

  /**
   * Find all the orders of a given user.
   *
   * @param buyerOpenId - the open id of the user
   * @param pageable - a Pageable object
   * @return all the orders of a given user
   */
  Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);
}
