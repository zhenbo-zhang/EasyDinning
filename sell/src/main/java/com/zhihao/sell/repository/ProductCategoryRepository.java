package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO layer of ProductCategory.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {  // <DataType, Type of Key>
  // Given a list of product category id, return a list of product category
  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
