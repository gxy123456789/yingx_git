package com.baizhi.gxy;

import com.baizhi.gxy.dao.AdminDao;
import com.baizhi.gxy.dao.UserMapper;
import com.baizhi.gxy.entity.User;
import com.baizhi.gxy.entity.UserExample;
import com.baizhi.gxy.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxGxyApplicationTests {

    @Autowired
    AdminDao adminDao;

    @Resource
    UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void contextLossads() {

        Integer integer = userMapper.selectByTime(1);
        System.out.println(integer);


    }

    @Test
    public void contextLoads() {
        List<User> users = userMapper.selectAll();
        for (User user : users) {
            System.out.println(user);
        }

    }

    @Test
    public void test1() {
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("1");

    }


    /*@Test
    public void contextLoads() {
        Admin admin = adminDao.queryUsername("admin","admin");
        System.out.println(admin);
    }

    @Test
    public void  test1(){
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo("1");
        List<User> users = userMapper.selectByExample(example);
        for (User user : users) {
            System.out.println(user);
        }
    }*/

}
