package com.zhihao.sell.service.impl;

import com.zhihao.sell.dataobject.OrderDetail;
import com.zhihao.sell.dto.OrderDTO;
import com.zhihao.sell.enums.OrderStatusEnum;
import com.zhihao.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

  @Autowired
  private OrderServiceImpl orderService;

  private final String BUYER_OPENID = "110110";

  private final String ORDER_ID = "1530963528165477925";

  @Test
  public void create() {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setBuyerName("Zhihao");
    orderDTO.setBuyerPhone("13162777819");
    orderDTO.setBuyerAddress("WA");
    orderDTO.setBuyerOpenid(BUYER_OPENID);

    List<OrderDetail> orderDetailList = new ArrayList<>();

    OrderDetail o1 = new OrderDetail();
    o1.setProductId("1");
    o1.setProductQuantity(1);
    OrderDetail o2 = new OrderDetail();
    o2.setProductId("2345");
    o2.setProductQuantity(101);
    orderDetailList.add(o1);
    orderDetailList.add(o2);
    orderDTO.setOrderDetailList(orderDetailList);

    OrderDTO result = orderService.create(orderDTO);

    log.info("【Create Order】 result={}", result);
    Assert.assertNotNull(result);
  }

  @Test
  public void findOne() {
    OrderDTO result = orderService.findOne(ORDER_ID);
    log.info("查询单个订单 result={}", result);
    Assert.assertEquals(ORDER_ID, result.getOrderId());
  }

  @Test
  public void findList() {
    PageRequest request = new PageRequest(0, 2);
    Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
    Assert.assertNotEquals(0, orderDTOPage.getTotalElements());

  }

  @Test
  public void cancel() {
    OrderDTO orderDTO = orderService.findOne(ORDER_ID);
    OrderDTO result = orderService.cancel(orderDTO);
    Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
  }

  @Test
  public void finish() {
    OrderDTO orderDTO = orderService.findOne(ORDER_ID);
    OrderDTO result = orderService.finish(orderDTO);
    Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
  }

  @Test
  public void paid() {
    OrderDTO orderDTO = orderService.findOne(ORDER_ID);
    OrderDTO result = orderService.paid(orderDTO);
    Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
  }

  @Test
  public void list() {
    PageRequest request = new PageRequest(0, 2);
    Page<OrderDTO> orderDTOPage = orderService.findList(request);
    Assert.assertNotEquals(0, orderDTOPage.getTotalElements());

  }
}