package com.zhihao.sell.enums;

import lombok.Getter;

/**
 * Status of the order.
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
  NEW(0, "NEW"),
  FINISHED(1, "FINISHED"),
  CANCEL(2, "CANCELED");

  private Integer code;

  private String message;

  OrderStatusEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

}
