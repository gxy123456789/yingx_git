package com.baizhi.gxy.dao;

import com.baizhi.gxy.entity.Admin;

public interface AdminDao {

    Admin queryByUsername(String username);

}
