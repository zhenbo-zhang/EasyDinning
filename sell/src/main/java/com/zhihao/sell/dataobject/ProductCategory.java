package com.zhihao.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Class ProductCategory, contains all the information of the product_category table in database.
 */

// Mapping data in DB to an object in Java.
@Entity

// Use to update time automatically.
@DynamicUpdate

// Lombok annotation, creating getter and setter by default.
@Data
public class ProductCategory {  // "product_category" in database

  /**
   * category id.
   */
  @Id // key
  @GeneratedValue // auto-increment key
  private Integer categoryId;

  /**
   * category name.
   */
  private String categoryName;

  /**
   * category type.
   */
  private Integer categoryType;

  /**
   * create time.
   */
  private Date createTime;

  /**
   * update time.
   */
  private Date updateTime;

  /**
   * Constructor of ProductCategory.
   */
  public ProductCategory() {
  }

  /**
   * Constructor of ProductCategory.
   */
  public ProductCategory(String categoryName, Integer categoryType) {
    this.categoryName = categoryName;
    this.categoryType = categoryType;
  }
}
