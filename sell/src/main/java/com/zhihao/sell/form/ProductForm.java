package com.zhihao.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Class ProductForm used to handle data submitted from form.
 */
@Data
public class ProductForm {

  private String productId;

  /**
   * name.
   */
  private String productName;

  /**
   * price.
   */
  private BigDecimal productPrice;

  /**
   * stock.
   */
  private Integer productStock;

  /**
   * description.
   */
  private String productDescription;

  /**
   * image.
   */
  private String productIcon;

  /**
   * 类目编号.
   */
  private Integer categoryType;
}
