package com.baizhi.gxy.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.gxy.annotation.AddCache;
import com.baizhi.gxy.annotation.AddLog;
import com.baizhi.gxy.annotation.DelCahe;
import com.baizhi.gxy.dao.UserMapper;
import com.baizhi.gxy.entity.User;
import com.baizhi.gxy.entity.UserExample;
import com.baizhi.gxy.service.UserService;
import com.baizhi.gxy.util.AliyunOssUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public String add(User user) {
        String uid = UUID.randomUUID().toString();
        user.setId(uid);
        user.setStatus("1");
        user.setCreateDate(new Date());
        userMapper.insert(user);
        return uid;
    }

    @Override
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request) {
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //2获取文件名
        String filename = headImg.getOriginalFilename();

        String newName = new Date().getTime() + "-" + filename;

        try {
            //3.文件上传
            headImg.transferTo(new File(realPath, newName));

            //4.图片修改
            //修改的条件
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(id);

            User user = new User();
            user.setHeadImg(newName); //设置修改的结果

            //修改
            userMapper.updateByExampleSelective(user, example);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @AddCache
    @AddLog(value = "查询用户")
    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        UserExample example = new UserExample();
        Integer records = userMapper.selectCountByExample(example);
        map.put("records", records);
        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);

        //当前页   page
        map.put("page", page);

        //数据     rows
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userMapper.selectByRowBounds(new User(), rowBounds);
        map.put("rows", users);

        return map;
    }

    @DelCahe
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @DelCahe
    @Override
    public void delete(User user) {
        userMapper.delete(user);
    }

    @Override
    public void uploadUserAliyun(MultipartFile headImg, String id, HttpServletRequest request) {
        //将文件转为byte数组
        byte[] bytes = null;
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName = new Date().getTime() + "-" + filename;

        //1.文件上传至阿里云
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FqcFRX15KLk17Sux2gP";
        String accessKeySecret = "TzsCdRDD9L9Vmtm4PN489becPqbI1f";
        String bucket = "yingxapp";   //存储空间名
        String fileName = newName;  //指定上传文件名  可以指定上传目录
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传Byte数组。
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));
        // 关闭OSSClient。
        ossClient.shutdown();
        //2.图片信息的修改
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg("https://yingxapp.oss-cn-beijing.aliyuncs.com/" + newName);  //设置修改的结果   网络路径
        //https://yingxapp.oss-cn-beijing.aliyuncs.com/1585717855763-1585298568675-10.jpg

        //修改
        userMapper.updateByExampleSelective(user, example);

    }

    @Override
    public void uploadUserAliyuns(MultipartFile headImg, String id, HttpServletRequest request) {
        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName = new Date().getTime() + "-" + filename;

        //1.文件上传至阿里云
        AliyunOssUtil.uploadFileBytes("yingxapp", headImg, newName);

        //截取视频第一帧
        //上传封面

        //2.图片信息的修改
        //修改的条件
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(id);

        User user = new User();
        user.setHeadImg("https://yingxapp.oss-cn-beijing.aliyuncs.com/" + newName);  //设置修改的结果   网络路径
        //https://yingxapp.oss-cn-beijing.aliyuncs.com/1585717855763-1585298568675-10.jpg

        //修改
        userMapper.updateByExampleSelective(user, example);
    }

    @AddCache
    @Override
    public List<User> shouAll() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public List<Integer> selectByTime(Integer time) {
        List<Integer> list = new ArrayList<>();
        //判断四周活跃人数
        Integer oneWeek = userMapper.selectByTime(7);
        Integer twoWeek = userMapper.selectByTime(14);
        Integer threeWeek = userMapper.selectByTime(21);
        Integer fourWeek = userMapper.selectByTime(28);
        list.add(oneWeek);
        list.add(twoWeek);
        list.add(threeWeek);
        list.add(fourWeek);
        return list;
    }


}
