package com.baizhi.gxy.controller;

import com.baizhi.gxy.entity.City;
import com.baizhi.gxy.entity.Mondel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("echarts")
public class EchartsController {

    @RequestMapping("queryUserNum")
    public HashMap<String, Object> queryUserNum() {
        //select concat(month(create_date),'月'),count(id) from yx_user where sex='女'
        //group by month(create_date)
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月"));
        map.put("boys", Arrays.asList(100, 200, 300, 500, 600, 800));
        map.put("girls", Arrays.asList(150, 260, 562, 415, 450, 300));
        return map;
    }

    @RequestMapping("queryUserMap")
    public ArrayList<Object> queryUserMap() {
        //select city name,count(id) value from yx_user where sex='女' GROUP BY city
        ArrayList<Object> list = new ArrayList<>();

        //根据性别分组查询  where=男  group by="cityName"
        ArrayList<City> boysCities = new ArrayList<>();
        boysCities.add(new City("北京", "500"));
        boysCities.add(new City("河南", "800"));
        boysCities.add(new City("湖南", "300"));
        boysCities.add(new City("湖北", "600"));
        boysCities.add(new City("山东", "100"));

        Mondel boyMondel = new Mondel("小男孩", boysCities);

        ArrayList<City> girlsCities = new ArrayList<>();
        girlsCities.add(new City("黑龙江", "400"));
        girlsCities.add(new City("吉林", "800"));
        girlsCities.add(new City("山西", "700"));
        girlsCities.add(new City("重庆", "600"));
        girlsCities.add(new City("海南", "500"));

        Mondel girlMondel = new Mondel("小姑娘", girlsCities);

        list.add(boyMondel);
        list.add(girlMondel);

        return list;
    }
}
