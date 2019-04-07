package com.zhihao.sell.exception;

import com.zhihao.sell.enums.ResultEnum;

/**
 * Exception SellException.
 */

public class SellException extends RuntimeException {

  /**
   * Exception code.
   */
  private Integer code;

  public SellException(ResultEnum resultEnum) {
    super(resultEnum.getMessage());
    this.code = resultEnum.getCode();
  }

  public SellException(Integer code, String message) {
    super(message);
    this.code = code;
  }
}
