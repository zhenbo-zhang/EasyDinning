package com.zhihao.sell.controller;

import com.zhihao.sell.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skill")
@Slf4j
public class SeckillController {

  @Autowired
  private SeckillService seckillService;

  /**
   * Check product which is for second kill
   *
   * @param productId - the id of the product
   * @return product which is for second kill
   */
  @GetMapping("/query/{productId}")
  public String query(@PathVariable String productId) throws Exception {
    return seckillService.querySecKillProductInfo(productId);
  }

  /**
   * Second kill. If successful, the product will be returned.
   *
   * @param productId - the id of the product
   * @return the product
   * @throws Exception - thrown if error happens
   */
  @GetMapping("/order/{productId}")
  public String skill(@PathVariable String productId) throws Exception {
    log.info("@skill request,productId={}", productId);
    seckillService.orderProductMockDiffUser(productId);
    return seckillService.querySecKillProductInfo(productId);
  }

}
