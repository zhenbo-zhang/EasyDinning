package com.zhihao.sell.service;

import com.zhihao.sell.dataobject.ProductInfo;
import com.zhihao.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * DAO for ProductInfo
 */
public interface ProductService {

  /**
   * Find one Product.
   *
   * @param productId - the id of the product
   * @return one Product
   */
  ProductInfo findOne(String productId);

  /**
   * check all orders on sale
   */
  List<ProductInfo> findUpAll();

  /**
   * check all orders on sale for pagination.
   *
   * @return all orders on sale with pagination
   */
  Page<ProductInfo> findAll(Pageable pageable);

  /**
   * save the ProductInfo.
   *
   * @param productInfo - ProductInfo to save.
   * @return the saved ProductInfo
   */
  ProductInfo save(ProductInfo productInfo);

  /*add stock*/
  void increaseStock(List<CartDTO> cartDTOList);

  /*decrease stock*/
  void decreaseStock(List<CartDTO> cartDTOList);

  // on sale
  ProductInfo onSale(String productId);

  // off sale
  ProductInfo offSale(String productId);

}
