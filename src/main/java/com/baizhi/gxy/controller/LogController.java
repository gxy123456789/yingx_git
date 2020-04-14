package com.baizhi.gxy.controller;


import com.baizhi.gxy.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
@RequestMapping("log")
public class LogController {
    @Resource
    LogService logService;

    @RequestMapping("queryBuPager")
    @ResponseBody
    HashMap<String, Object> queryBuPager(Integer rows, Integer page) {
        HashMap<String, Object> map = logService.queryByPage(page, rows);
        return map;
    }

}
