package com.zhihao.sell.utils;

import java.util.Random;

/**
 * Utility class used to generate random key.
 */
public class KeyUtil {

  // Generate unique key (Time + random number).
  // synchronized to handle concurrency
  public static synchronized String genUniqueKey() {
    Random random = new Random();

    // 6 digits
    Integer number = random.nextInt(900000) + 100000;

    return System.currentTimeMillis() + String.valueOf(number);
  }
}
