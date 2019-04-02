package com.zhihao.sell.enums;

import lombok.Getter;

/**
 * Payment status of the order.
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

  WAIT(0, "WAIT"),
  SUCCESS(1, "SUCCESS");

  private Integer code;

  private String message;

  PayStatusEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
