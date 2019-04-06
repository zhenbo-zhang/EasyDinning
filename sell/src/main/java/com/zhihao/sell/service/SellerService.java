package com.zhihao.sell.service;

import com.zhihao.sell.dataobject.SellerInfo;

public interface SellerService {

  /**
   * Return the information of the seller with the open id.
   *
   * @param openid - the open id of the seller
   * @return the information of the seller with the open id
   */
  SellerInfo findSellerInfoByOpenid(String openid);
}

