package com.baizhi.gxy.controller;

import com.baizhi.gxy.entity.Category;
import com.baizhi.gxy.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @RequestMapping("showAllcategory")
    ResponseEntity queryBuPager(Integer rows, Integer page) {
        HashMap<String, Object> map = categoryService.CategoryqueryByPage(page, rows);
        ResponseEntity.status(HttpStatus.ACCEPTED);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("queryBuPagerTwo")
    ResponseEntity queryBuPagerTwo(Integer rows, Integer page, String parent_id) {
        HashMap<String, Object> map = categoryService.CategoryqueryByPageTWO(page, rows, parent_id);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("edit")
    public Object edit(Category category, String oper) {
        if (oper.equals("add")) {
            categoryService.add(category);
        }
        if (oper.equals("edit")) {
            categoryService.update(category);
        }
        if (oper.equals("del")) {
            HashMap<String, Object> map = categoryService.delete(category);
            System.out.println(map.get("message"));
            return map;
        }
        return null;
    }
}
