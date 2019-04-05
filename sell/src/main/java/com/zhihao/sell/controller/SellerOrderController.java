package com.zhihao.sell.controller;

import com.zhihao.sell.dto.OrderDTO;
import com.zhihao.sell.enums.ResultEnum;
import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

  @Autowired
  private OrderService orderService;

  /**
   * List order.
   *
   * @param page - page, start from 1
   * @param size - how many data in one page
   * @param map - a map to store result
   * @return the list of order ModelAndView
   */
  @GetMapping("/list")
  public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      Map<String, Object> map) {
    // PageRequest start at 0, so -1 here
    PageRequest request = new PageRequest(page - 1, size);
    Page<OrderDTO> orderDTOPage = orderService.findList(request);
    map.put("orderDTOPage", orderDTOPage);
    // send the current page to list.ftl, so it can check the current page
    map.put("currentPage", page);
    // send the size of a page to list.ftl, so it can check the current page
    map.put("size", size);
//        orderDTOPage.getTotalPages()
    return new ModelAndView("order/list", map);
  }


  /**
   * Cancel an order.
   *
   * @param orderId - the id of the order to be canceled
   * @param map - map used in ftl
   * @return a ModelAndView object
   */
  @GetMapping("/cancel")
  public ModelAndView cancel(@RequestParam("orderId") String orderId,
      Map<String, Object> map) {
    try {
      OrderDTO orderDTO = orderService.findOne(orderId);
      orderService.cancel(orderDTO);
    } catch (SellException e) {
      log.error("【Cancel Order】Unexpected Error{}", e);
      // Transfer error message and redirected url to the error template
      map.put("msg", e.getMessage());
      map.put("url", "/sell/seller/order/list");
      return new ModelAndView("common/error", map);
    }
    // Canceling the order is successful right now
    map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
    map.put("url", "/sell/seller/order/list");
    return new ModelAndView("common/success");
  }

  /**
   * Check the detail of an order.
   *
   * @param orderId - the id of the order
   * @param map - the map used to transfer data to template
   * @return an ModelAndView object
   */

  @GetMapping("/detail")
  public ModelAndView detail(@RequestParam("orderId") String orderId,
      Map<String, Object> map) {
    OrderDTO orderDTO = new OrderDTO();
    try {
      orderDTO = orderService.findOne(orderId);
    } catch (SellException e) {
      log.error("【Check Order Detail】Unexpected Error{}", e);
      map.put("msg", e.getMessage());
      map.put("url", "/sell/seller/order/list");
      return new ModelAndView("common/error", map);
    }

    map.put("orderDTO", orderDTO);
    return new ModelAndView("order/detail", map);
  }

  // finish order

  @GetMapping("/finish")
  public ModelAndView finished(@RequestParam("orderId") String orderId,
      Map<String, Object> map) {
    try {
      OrderDTO orderDTO = orderService.findOne(orderId);
      orderService.finish(orderDTO);
    } catch (SellException e) {
      log.error("【Finish Order】Unexpected Error{}", e);
      map.put("msg", e.getMessage());
      map.put("url", "/sell/seller/order/list");
      return new ModelAndView("common/error", map);
    }

    map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
    map.put("url", "/sell/seller/order/list");
    return new ModelAndView("common/success");
  }

}
