package com.baizhi.gxy.service;

import com.baizhi.gxy.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UserService {

    String add(User user);

    void uploadUser(MultipartFile headImg, String id, HttpServletRequest request);

    //用户展示所有
    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    //修改
    void update(User user);

    //删除
    public void delete(User user);

    void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request);

    //上传文件到阿里云
    void uploadUserAliyuns(MultipartFile headImg, String id, HttpServletRequest request);

    //easyPoi
    List<User> shouAll();

    List<Integer> selectByTime(Integer time);
}
