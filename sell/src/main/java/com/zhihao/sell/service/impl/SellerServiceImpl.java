package com.zhihao.sell.service.impl;

import com.zhihao.sell.dataobject.SellerInfo;
import com.zhihao.sell.repository.SellerInfoRepository;
import com.zhihao.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for SellerService.
 */
@Service
public class SellerServiceImpl implements SellerService {

  @Autowired
  private SellerInfoRepository repository;

  /**
   * Return the information of the seller with the open id.
   *
   * @param openid - the open id of the seller
   * @return the information of the seller with the open id
   */
  @Override
  public SellerInfo findSellerInfoByOpenid(String openid) {
    return repository.findByOpenid(openid);
  }
}
