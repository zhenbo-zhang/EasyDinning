package com.zhihao.sell.utils;

import com.zhihao.sell.enums.CodeEnum;

/**
 * Cla
 */
public class EnumUtil {

  public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
    for (T each : enumClass.getEnumConstants()) {
      if (code.equals(each.getCode())) {
        return each;
      }
    }
    return null;
  }
}
