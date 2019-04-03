package com.zhihao.sell.dataobject;

import com.zhihao.sell.enums.OrderStatusEnum;
import com.zhihao.sell.enums.PayStatusEnum;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {

  /**
   * ID of the order.
   */
  @Id
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
   * Open ID of the buyer.
   */
  private String buyerOpenid;

  /**
   * Total amount of the buyer.
   */
  private BigDecimal orderAmount;

  /**
   * Status of the order.
   */
  private Integer orderStatus = OrderStatusEnum.NEW.getCode();

  /**
   * Payment status of the order.
   */
  private Integer payStatus = PayStatusEnum.WAIT.getCode();

  /**
   * Create time of the order.
   */
  private Date createTime;

  /**
   * Update time of the order.
   */
  private Date updateTime;


}
