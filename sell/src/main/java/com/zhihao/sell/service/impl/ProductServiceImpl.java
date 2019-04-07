package com.zhihao.sell.service.impl;

import com.zhihao.sell.dataobject.ProductInfo;
import com.zhihao.sell.dto.CartDTO;
import com.zhihao.sell.enums.ProductStatusEnum;
import com.zhihao.sell.enums.ResultEnum;
import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.repository.ProductInfoRepository;
import com.zhihao.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation for ProductService.
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductInfoRepository repository;

  /**
   * Find one Product.
   *
   * @param productId - the id of the product
   * @return one Product
   */
  @Override
  @Cacheable(cacheNames = "product", key = "123")
  // if not including key, it will automatically be filled with the argument, which is productId.
  public ProductInfo findOne(String productId) {
    return repository.findOne(productId);
  }

  /**
   * check all orders on sale
   */
  @Override
  public List<ProductInfo> findUpAll() {
    return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
  }

  /**
   * check all orders on sale for pagination.
   *
   * @return all orders on sale with pagination
   */
  @Override
  public Page<ProductInfo> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  /**
   * save the ProductInfo.
   *
   * @param productInfo - ProductInfo to save.
   * @return the saved ProductInfo
   */
  @Override
  @CachePut(cacheNames = "product", key = "123")
  public ProductInfo save(ProductInfo productInfo) {
    return repository.save(productInfo);
  }


  /**
   * Increase the stock of the products in the list.
   *
   * @param cartDTOList - a list of cart DTO
   */
  @Override
  @Transactional
  public void increaseStock(List<CartDTO> cartDTOList) {
    for (CartDTO cartDTO : cartDTOList) {
      ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
      if (productInfo == null) {
        throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
      }
      Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
      productInfo.setProductStock(result);
      repository.save(productInfo);
    }
  }

  /**
   * Decrease the stock of a lot of cart DTO.
   *
   * @param cartDTOList - a list of cart DTO
   */
  @Override
  // Roll back if there is exception.
  @Transactional
  public void decreaseStock(List<CartDTO> cartDTOList) {
    for (CartDTO cartDTO : cartDTOList) {
      ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
      if (productInfo == null) {
        throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
      }
      Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
      if (result < 0) {
        throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
      }
      productInfo.setProductStock(result);
      repository.save(productInfo);
    }
  }

  /**
   * Change the status of the product from off sale to on sale.
   *
   * @param productId - the id of the product
   * @return the product info
   */
  @Override
  public ProductInfo onSale(String productId) {
    ProductInfo productInfo = repository.findOne(productId);
    if (productInfo == null) {
      throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
    }
    if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
      throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
    }

    // update
    productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
    return repository.save(productInfo);
  }

  /**
   * Change the status of the product from on sale to off sale.
   *
   * @param productId - the id of the product
   * @return the product info
   */
  @Override
  public ProductInfo offSale(String productId) {
    ProductInfo productInfo = repository.findOne(productId);
    if (productInfo == null) {
      throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
    }
    if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
      throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
    }

    // update
    productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
    return repository.save(productInfo);
  }
}
