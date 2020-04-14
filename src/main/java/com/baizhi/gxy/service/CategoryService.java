package com.baizhi.gxy.service;

import com.baizhi.gxy.entity.Category;

import java.util.HashMap;

public interface CategoryService {

    //用户展示所有一级
    HashMap<String, Object> CategoryqueryByPage(Integer page, Integer rows);

    //用户展示所有二级
    HashMap<String, Object> CategoryqueryByPageTWO(Integer page, Integer rows, String parent_id);

    HashMap<String, Object> delete(Category category);

    void add(Category category);

    void update(Category category);
}
