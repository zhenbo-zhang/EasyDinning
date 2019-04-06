package com.zhihao.sell.dataobject.mapper;

import static org.junit.Assert.*;

import com.zhihao.sell.dataobject.ProductCategory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

  @Autowired
  private ProductCategoryMapper productCategoryMapper;

  @Test
  public void insertByMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("category_name", "abc");
    map.put("category_type", 101);
    int result = productCategoryMapper.insertByMap(map);
    assertEquals(1, result);
  }

  @Test
  public void insertByObject() {
    ProductCategory productCategory = new ProductCategory();
    productCategory.setCategoryName("abc");
    productCategory.setCategoryType(102);
    int result = productCategoryMapper.insertByObject(productCategory);
    assertEquals(1, result);
  }

  @Test
  public void findByCategoryType() {
    ProductCategory productCategory = productCategoryMapper.findByCategoryType(102);
    assertNotNull(productCategory);
  }

  @Test
  public void findByCategoryName() {
    List<ProductCategory> result = productCategoryMapper.findByCategoryName("abc");
    assertNotEquals(0, result.size());
  }

  @Test
  public void updateByCategoryType() {
    int result = productCategoryMapper.updateByCategoryType("cde", 101);
    assertEquals(1, result);
  }

  @Test
  public void updateByObject() {
    ProductCategory productCategory = new ProductCategory();
    productCategory.setCategoryName("abc");
    productCategory.setCategoryType(102);
    int result = productCategoryMapper.updateByObject(productCategory);
    assertEquals(1, result);
  }

  @Test
  public void deleteByCategoryType() {
    int result = productCategoryMapper.deleteByCategoryType(102);
    assertEquals(1, result);
  }

  @Test
  public void selectByCategory() {
    ProductCategory productCategory = productCategoryMapper.selectByCategory(101);
    assertNotNull(productCategory);
  }
}