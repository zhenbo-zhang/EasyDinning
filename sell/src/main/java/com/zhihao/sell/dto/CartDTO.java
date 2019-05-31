package com.zhihao.sell.dto;

import lombok.Data;

/**
 * Data Transfer Object for Cart (Shopping cart). Used to decrease stock.
 */
@Data
public class CartDTO {

  private String productId;

  private Integer productQuantity;

  public CartDTO(String productId, Integer productQuantity) {
    this.productId = productId;
    this.productQuantity = productQuantity;
  }
}
