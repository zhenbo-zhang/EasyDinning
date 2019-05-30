package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO layer of ProductInfoRepository.<DataType, Type of Key>
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
//Return the list of product have a status productStatus
  List<ProductInfo> findByProductStatus(Integer productStatus);
}
