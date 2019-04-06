package com.zhihao.sell.dataobject.mapper;

import com.zhihao.sell.dataobject.ProductCategory;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Interface ProductCategoryMapper, use my batis to query the database.
 */
public interface ProductCategoryMapper {

  // Insert data by map.
  @Insert("insert into product_category(category_name, category_type) values (#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
  int insertByMap(Map<String, Object> map);

  // Insert data by object
  @Insert("insert into product_category(category_name, category_type) values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
  int insertByObject(ProductCategory productCategory);

  // Query data with key
  @Select("select * from product_category where category_type = #{categoryType}")
  @Results({
      @Result(column = "category_id", property = "categoryId"),
      @Result(column = "category_name", property = "categoryName"),
      @Result(column = "category_type", property = "categoryType")
  })
  ProductCategory findByCategoryType(Integer categoryType);

  // Query data with name
  @Select("select * from product_category where category_name = #{categoryName}")
  @Results({
      @Result(column = "category_id", property = "categoryId"),
      @Result(column = "category_name", property = "categoryName"),
      @Result(column = "category_type", property = "categoryType")
  })
  List<ProductCategory> findByCategoryName(String categoryName);

  // Update the category with key
  @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
  int updateByCategoryType(@Param("categoryName") String categoryName,
      @Param("categoryType") Integer categoryType);

  // Update the category with object
  @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
  int updateByObject(ProductCategory productCategory);

  // Delete by key
  @Delete("delete from product_category where category_type = #{categoryType}")
  int deleteByCategoryType(Integer categoryType);

  // get ProductCategory using mybatis xml
  ProductCategory selectByCategory(Integer categoryType);
}

