package com.zhihao.sell.utils;

import com.zhihao.sell.enums.CodeEnum;

/**
 * Utility class EnumUtil, which will return the proper Enum based on the code the enumClass.
 */
public class EnumUtil {

  /**
   * Return the enum entry given the code and enumClass.
   *
   * @param code - the code of the enum entry
   * @param enumClass - the enum
   * @param <T> - type of the enum
   * @return the enum entry given the code and enumClass
   */
  public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
    for (T each : enumClass.getEnumConstants()) {
      if (code.equals(each.getCode())) {
        return each;
      }
    }
    return null;
  }
}
