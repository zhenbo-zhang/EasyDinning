package com.zhihao.sell.form;

import lombok.Data;

@Data
public class CategoryForm {

    private Integer categoryId;

    /** category name */
    private String categoryName;

    /** category type. */
    private Integer categoryType;
}
