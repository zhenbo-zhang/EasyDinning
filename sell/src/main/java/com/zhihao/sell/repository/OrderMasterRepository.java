package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao layers of OrderMaster.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

  /**
   * Find all the orders of a given user.
   *
   * @param buyerOpenId - the open id of the user
   * @param pageable - a Pageable object used for pagination. It is an interface, PageRequest is a concrete class (page, size).
   * @return all the orders of a given user
   */
  Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);
}
