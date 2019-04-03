package com.zhihao.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhihao.sell.dataobject.OrderDetail;
import com.zhihao.sell.enums.OrderStatusEnum;
import com.zhihao.sell.enums.PayStatusEnum;
import com.zhihao.sell.utils.EnumUtil;
import com.zhihao.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object of Order (to and from front end)
 */
@Data
public class OrderDTO {

  /**
   * ID of the order.
   */
  private String orderId;

  /**
   * Name of the buyer.
   */
  private String buyerName;

  /**
   * Phone of the buyer.
   */
  private String buyerPhone;

  /**
   * Address of the buyer.
   */
  private String buyerAddress;

  /**
   * Open id of the buyer.
   */
  private String buyerOpenid;

  /**
   * Amount of the buyer.
   */
  private BigDecimal orderAmount;

  /**
   * Status of the order.
   */
  private Integer orderStatus;

  /**
   * Pay status of the order.
   */
  private Integer payStatus;

  /**
   * Create time of the order.
   */
  @JsonSerialize(using = Date2LongSerializer.class)
  private Date createTime;

  /**
   * Update time of the order. Using JsonSerialize to convert time to its proper representation
   * (/1000).
   */
  @JsonSerialize(using = Date2LongSerializer.class)
  private Date updateTime;

  /**
   * List of order details. Using JsonSerialize to convert time to its proper representation
   * (/1000).
   */
  List<OrderDetail> orderDetailList;

  @JsonIgnore
  public OrderStatusEnum getOrderStatusEnum() {
    return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
  }

  @JsonIgnore
  public PayStatusEnum getPayStatusEnum() {
    return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
  }
}
