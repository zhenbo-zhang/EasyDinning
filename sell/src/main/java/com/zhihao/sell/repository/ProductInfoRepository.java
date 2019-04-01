package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO layer of ProductInfoRepository.
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

  List<ProductInfo> findByProductStatus(Integer productStatus);
}
