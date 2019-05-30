package com.zhihao.sell.service;

import com.zhihao.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * Service layer. Interface CategoryService.
 */
public interface CategoryService {

  /**
   * Return one ProductCategory.
   *
   * @param categoryId - the ID of the ProductCategory
   * @return one ProductCategory
   */
  ProductCategory findOne(Integer categoryId);

  /**
   * Return all ProductCategory.
   *
   * @return all ProductCategories
   */
  List<ProductCategory> findAll();

  /**
   * Return a list of ProductCategory.
   *
   * @param categoryTypeList - a list of ProductCategory ID
   * @return a list of ProductCategory
   */
  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

  /**
   * create or update the productCategory.
   *
   * @param productCategory - the productCategory to save or update
   * @return the saved productCategory
   */
  ProductCategory save(ProductCategory productCategory);
}
