package com.baizhi.gxy.serviceImpl;

import com.baizhi.gxy.dao.AdminDao;
import com.baizhi.gxy.entity.Admin;
import com.baizhi.gxy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Resource
    HttpSession session;

    @Autowired
    AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> login(Admin admin, String enCode) {
        HashMap<String, Object> map = new HashMap<>();

        //1.获取存储的验证码
        String imageCode = (String) session.getAttribute("imageCode");
        //2.验证验证码
        if (imageCode.equals(enCode)) {
            //3.验证用户
            //查询用户
            Admin admins = adminDao.queryByUsername(admin.getUsername());
            if (admins != null) {
                //4.验证密码
                if (admin.getPassword().equals(admins.getPassword())) {

                    //存储用户信息
                    session.setAttribute("admin", admins);

                    map.put("status", "200");
                    map.put("message", "登陆成功");
                } else {
                    map.put("status", "400");
                    map.put("message", "密码错误");
                }
            } else {
                map.put("status", "400");
                map.put("message", "用户不存在");
            }
        } else {
            //验证码不正确，返回错误信息
            map.put("status", "400");
            map.put("message", "验证码错误");
        }

        return map;
    }

}
