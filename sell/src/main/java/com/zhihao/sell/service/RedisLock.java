package com.zhihao.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Class RedisLock, utility class for distributed lock using redis.
 */
@Component
@Slf4j
public class RedisLock {

  /**
   * Redis service.
   */
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * Lock
   *
   * @param key - productId
   * @param value current time + expiration time
   */
  public boolean lock(String key, String value) {
    if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) { //setnx (if not previously set, return true; otherwise, return false)
      // Set the lock successfully (which means key doesn't exist)
      return true;
    }

    // Check whether the lock expires, in case of error happens in the locked code
    // and unlock cannot not be executed, which results in deadlock.
    String currentValue = stringRedisTemplate.opsForValue().get(key);
    // If the lock expires.
    if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System
        .currentTimeMillis()) { //currentValue is not null and less than current time
      // Get the value of the previous lock
      String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value); //getset

      // Assuming two threads go into this piece. and the key is already taken. currentValue=A, the values of the two threads are B. The lock expires.
      // One of the two threads will executes getAndSet first. After this，the value becomes B. Only the first thread will get A (and get the lock), the other one will get B.
      // it can also prevent dead lock if there is a break point in sec kill.
      if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
        //oldValue is not null, and oldValue == currentValue.
        return true;
      }
    }
    return false;
  }


  /**
   * Unlock.
   *
   * @param key - product id
   * @param value - current time + expiration time
   */
  public void unlock(String key, String value) {
    try {
      String currentValue = stringRedisTemplate.opsForValue().get(key);
      if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
        stringRedisTemplate.opsForValue().getOperations().delete(key); //delete key
      }
    } catch (Exception e) {
      log.error("[Redis distributed locking] unlocking errors，{}", e);
    }
  }

}
