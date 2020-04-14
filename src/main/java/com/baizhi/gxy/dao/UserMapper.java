package com.baizhi.gxy.dao;

import com.baizhi.gxy.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    List<User> selectEasy(User user);

    //判断活跃用户
    Integer selectByTime(Integer time);
}