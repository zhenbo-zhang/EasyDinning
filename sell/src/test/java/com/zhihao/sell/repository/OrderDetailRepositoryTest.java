package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

  @Autowired
  private OrderDetailRepository orderDetailRepository;

  @Test
  public void saveTest() {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setDetailId("1234567810");
    orderDetail.setOrderId("11111112");
    orderDetail.setProductIcon("http://xxxx.jpg");
    orderDetail.setProductId("11112222");
    orderDetail.setProductName("burger");
    orderDetail.setProductPrice(new BigDecimal(2.2));
    orderDetail.setProductQuantity(3);
    OrderDetail result = orderDetailRepository.save(orderDetail);
    Assert.assertNotNull(result);
  }

  @Test
  public void findByOrderId() {
    List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId("11111112");
    Assert.assertNotEquals(0, orderDetailList.size());
  }
}