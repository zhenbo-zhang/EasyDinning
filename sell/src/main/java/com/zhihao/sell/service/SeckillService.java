package com.zhihao.sell.service;

/**
 * Interface SeckillService which is used for second kill.
 */

public interface SeckillService {

  /**
   * Check product that is for second kill.
   *
   * @param productId - id of the product
   * @return the product that is for second kill
   */
  String querySecKillProductInfo(String productId);

  /**
   * A method that mock second kill by many users.
   *
   * @param productId - the id of the product
   */
  void orderProductMockDiffUser(String productId);
}
