package com.zhihao.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Information about a product needed to transit to front end. No need to transit stock info.
 */
@Data
public class ProductInfoVO {

  // Using JsonProperty to transform the data to front end as "id"
  @JsonProperty("id")
  private String productId;

  // Using JsonProperty to transform the data to front end as "name"
  @JsonProperty("name")
  private String productName;

  // Using JsonProperty to transform the data to front end as "price"
  @JsonProperty("price")
  private BigDecimal productPrice;

  // Using JsonProperty to transform the data to front end as "description"
  @JsonProperty("description")
  private String productDescription;

  // Using JsonProperty to transform the data to front end as "icon"
  @JsonProperty("icon")
  private String productIcon;
}
