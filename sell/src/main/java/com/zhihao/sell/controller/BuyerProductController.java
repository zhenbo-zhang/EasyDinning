package com.zhihao.sell.controller;

import com.zhihao.sell.VO.ProductInfoVO;
import com.zhihao.sell.VO.ProductVO;
import com.zhihao.sell.VO.ResultVO;
import com.zhihao.sell.dataobject.ProductCategory;
import com.zhihao.sell.dataobject.ProductInfo;
import com.zhihao.sell.service.CategoryService;
import com.zhihao.sell.service.ProductService;
import com.zhihao.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.List;

// Json format
@RestController

/**
 *
 */
@RequestMapping("/buyer/product")
public class BuyerProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/list")
  public ResultVO list() {

    // check all the products

    List<ProductInfo> productInfoList = productService.findUpAll();

    // check needed categories (once)

    List<Integer> categoryTypeList = productInfoList.stream()
        .map(e -> e.getCategoryType())
        .collect(Collectors.toList());
    List<ProductCategory> productCategoryList = categoryService
        .findByCategoryTypeIn(categoryTypeList);

    // grouping every product under the category it belongs to

    List<ProductVO> productVOList = new ArrayList<>();
    for (ProductCategory productCategory : productCategoryList) {
      ProductVO productVO = new ProductVO();
      productVO.setCategoryType(productCategory.getCategoryType());
      productVO.setCategoryName(productCategory.getCategoryName());

      List<ProductInfoVO> productInfoVOList = new ArrayList<>();
      for (ProductInfo productInfo : productInfoList) {
        if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
          ProductInfoVO productInfoVO = new ProductInfoVO();
          // Copy the corresponding properties from productInfo to productInfoVO.
          BeanUtils.copyProperties(productInfo, productInfoVO);
          productInfoVOList.add(productInfoVO);
        }
      }
      productVO.setProductInfoVOList(productInfoVOList);
      productVOList.add(productVO);
    }

    return ResultVOUtil.success(productVOList);
  }
}
