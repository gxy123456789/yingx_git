package com.baizhi.gxy.service;

import com.baizhi.gxy.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    //后台：管理员登陆根据查询管理员
    HashMap<String, Object> login(Admin admin, String enCode);

}
