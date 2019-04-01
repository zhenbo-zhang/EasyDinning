package com.zhihao.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Class ProductCategory, contains all the rows of the ProductCategory table in database.
 */

// Mapping from DB to Java.
@Entity

// Use to update time.
@DynamicUpdate

// Lombok annotation, save the trouble of creating getter and setter every time.
@Data
public class ProductCategory {

  /**
   * category id.
   */
  @Id // key
  @GeneratedValue // auto-increment
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
