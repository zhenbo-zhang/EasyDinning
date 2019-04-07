package com.zhihao.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Used for OrderForm validation.
 */

@Data
public class OrderForm {

  /**
   * Buyer name.
   */
  @NotEmpty(message = "Name is Required")
  private String name;

  /**
   * Buyer phone.
   */
  @NotEmpty(message = "Phone Number is Required")
  private String phone;

  /**
   * Buyer address.
   */
  @NotEmpty(message = "Address is Required")
  private String address;

  /**
   * Buyer open id.
   */
  @NotEmpty(message = "Open Id is Required")
  private String openid;

  /**
   * Items.
   */
  @NotEmpty(message = "Shopping Cart is Required")
  private String items;

}
