package com.zhihao.sell.repository;

import com.zhihao.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

  @Autowired
  private ProductCategoryRepository productCategoryRepository;

  @Test
  public void findOneTest() {
    ProductCategory productCategory = productCategoryRepository.findOne(1);
    System.out.println(productCategory.toString());
  }

  @Test
  @Transactional
  public void saveTest() {
    ProductCategory productCategory = new ProductCategory("girls' favorite", 3);
    ProductCategory result = productCategoryRepository.save(productCategory);
    Assert.assertNotNull(result);
    Assert.assertNotEquals(null, result);
  }

  @Test
  public void findByCategoryTypeInTest() {
    List<Integer> list = Arrays.asList(2, 3, 4);
    List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(list);
    Assert.assertNotEquals(0, result.size());
  }
}