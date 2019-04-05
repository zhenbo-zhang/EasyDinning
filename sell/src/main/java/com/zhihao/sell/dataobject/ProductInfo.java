package com.zhihao.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhihao.sell.enums.ProductStatusEnum;
import com.zhihao.sell.utils.EnumUtil;
import com.zhihao.sell.enums.ProductStatusEnum;
import com.zhihao.sell.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Class ProductInfo, contains all the rows of the ProductInfo table in database.
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

  @Id
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
   * status
   */
  private Integer productStatus = ProductStatusEnum.UP.getCode();

  /**
   * category type.
   */
  private Integer categoryType;

  /**
   * create Time.
   */
  private Date createTime;

  /**
   * updateTime.
   */
  private Date updateTime;

  /**
   * Get the status of the product.
   * @return
   */
  @JsonIgnore
  public ProductStatusEnum getProductStatusEnum() {
    return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
  }
}
