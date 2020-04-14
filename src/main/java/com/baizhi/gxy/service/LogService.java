package com.baizhi.gxy.service;

import java.util.HashMap;

public interface LogService {

    //日志展示所有
    HashMap<String, Object> queryByPage(Integer page, Integer rows);
}
