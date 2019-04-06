package com.zhihao.sell.service.impl;


import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.service.RedisLock;
import com.zhihao.sell.service.SeckillService;
import com.zhihao.sell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

  @Autowired
  private RedisLock redisLock;

  /**
   * Expiration time == 10s.
   */
  private static final int TIMEOUT = 10 * 1000;

  /**
   * Special offer, only 100000
   */
  static Map<String, Integer> products; //product table
  static Map<String, Integer> stock;// stock table
  static Map<String, String> orders;// customer who make the purchase

  static {
    /**
     * stimulate product table, stock table and customer table.
     */
    products = new HashMap<>();
    stock = new HashMap<>();
    orders = new HashMap<>();
    products.put("123456", 100000);
    stock.put("123456", 100000);
  }

  private String queryMap(String productId) { //stimulate query the database
    return "Special offer, only"
        + products.get(productId)
        + ", Left:" + stock.get(productId)
        + ", and has already been bought: "
        + orders.size();
  }

  @Override
  public String querySecKillProductInfo(String productId) {
    return this.queryMap(productId);
  }

  // Distributed lock based on Redis: http://redis.cn/commands/setnx.html  http://redis.cn/commands/getset.html
  // SETNX - set a key value pair. If key doesn't exist，it equals SET. When key exists, do nothing.
  // GETSET - Get the previous value first, then set the value to a new one. If key doesn't exist, return null, then set value.
  @Override
  public void orderProductMockDiffUser(
      String productId) {

    // Lock
    long time = System.currentTimeMillis() + TIMEOUT;
    if (!redisLock.lock(productId, String.valueOf(time))) {
      throw new SellException(101, "Too busy at this moment, please try again later");
    }

    // 1.Check stock, if 0, end the special offer
    int stockNum = stock.get(productId);
    if (stockNum == 0) {
      throw new SellException(100, "special offer has finished");
    } else {
      // 2.make an order
      orders.put(KeyUtil.genUniqueKey(), productId);
      // 3. decrease stock
      stockNum =
          stockNum
              - 1; // If it was not handled properly. Under concurrency, decrease order will take time. New thread get the previous stock.
      try {
        Thread.sleep(100); // stimulate the time to query database
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      stock.put(productId, stockNum);
    }

    // Unlock
    redisLock.unlock(productId, String.valueOf(time));

  }


}