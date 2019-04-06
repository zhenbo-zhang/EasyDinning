package com.zhihao.sell.dataobject.dao;

import com.zhihao.sell.dataobject.mapper.ProductCategoryMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductCategoryDao {
  @Autowired
  ProductCategoryMapper productCategoryMapper;

  public int insertByMap(Map<String, Object> map) {
    return productCategoryMapper.insertByMap(map);
  }

}
