package com.zhihao.sell.controller;

import com.zhihao.sell.dataobject.ProductCategory;
import com.zhihao.sell.exception.SellException;
import com.zhihao.sell.form.CategoryForm;
import com.zhihao.sell.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller SellerCategoryController used to update category information.
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

  @Autowired
  private CategoryService categoryService;

  /**
   * Show all categories.
   *
   * @param map - map used to transfer data to front end
   * @return a ModelAndView object
   */
  @GetMapping("/list")
  public ModelAndView list(Map<String, Object> map) {
    List<ProductCategory> categoryList = categoryService.findAll();
    map.put("categoryList", categoryList);
    return new ModelAndView("category/list", map);
  }

  /**
   * Show a category.
   *
   * @param categoryId - the id of category
   * @param map - map used to transfer data to front end
   * @return a ModelAndView object
   */
  @GetMapping("/index")
  public ModelAndView index(
      @RequestParam(value = "categoryId", required = false) Integer categoryId,
      Map<String, Object> map) {
    if (categoryId != null) {
      ProductCategory productCategory = categoryService.findOne(categoryId);
      map.put("category", productCategory);
    }
    return new ModelAndView("category/index", map);
  }

  /**
   * Update and save
   *
   * @param form - form object used to handle data submitted from form
   * @param bindingResult - used to validate data transformed from form
   * @param map - used to transfer data to front end
   * @return a ModelAndView object
   */
  @PostMapping("/save")
  public ModelAndView save(@Valid CategoryForm form,
      BindingResult bindingResult,
      Map<String, Object> map) {
    if (bindingResult.hasErrors()) {
      map.put("msg", bindingResult.getFieldError().getDefaultMessage());
      map.put("url", "/sell/seller/category/index");
      return new ModelAndView("common/error", map);
    }

    ProductCategory productCategory = new ProductCategory();
    try {
      if (form.getCategoryId() != null) {
        productCategory = categoryService.findOne(form.getCategoryId());
      }
      // When adding a new category, category id is auto-increment, so don't need to assign a new id here
      BeanUtils.copyProperties(form, productCategory);
      categoryService.save(productCategory);
    } catch (SellException e) {
      map.put("msg", e.getMessage());
      map.put("url", "/sell/seller/category/index");
      return new ModelAndView("common/error", map);
    }

    map.put("url", "/sell/seller/category/list");
    return new ModelAndView("common/success", map);
  }


}
