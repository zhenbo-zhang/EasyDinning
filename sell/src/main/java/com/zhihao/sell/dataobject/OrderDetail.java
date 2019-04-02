package com.zhihao.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Class OrderDetail, which contains all the information of the details of an order.
 */
@Entity
@Data
public class OrderDetail {

  /**
   * ID of the detail.
   */
  @Id
  private String detailId;

  /**
   * ID of the order.
   */
  private String orderId;

  /**
   * ID of the product.
   */
  private String productId;

  /**
   * Name of the product.
   */
  private String productName;

  /**
   * Price of the product.
   */
  private BigDecimal productPrice;

  /**
   * Quantity of the product.
   */
  private Integer productQuantity;

  /**
   * Icon of the product.
   */
  private String productIcon;
}
