package com.zhihao.sell.form;

import lombok.Data;

/**
 * Class CategoryForm used to handled data submitted from form.
 */
@Data
public class CategoryForm {

  private Integer categoryId;

  /**
   * category name
   */
  private String categoryName;

  /**
   * category type.
   */
  private Integer categoryType;
}
