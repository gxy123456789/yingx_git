package com.baizhi.gxy.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.gxy.entity.User;
import com.baizhi.gxy.service.UserService;
import com.baizhi.gxy.util.AliyunSendPhoneUtil;
import com.baizhi.gxy.util.HttpClientUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("edit")
    @ResponseBody
    public String edit(User user, String oper, String id) {

        String uid = null;
        if (oper.equals("add")) {
            System.out.println(user);
            uid = userService.add(user);
        }
        if (oper.equals("edit")) {
            userService.update(user);
        }
        if (oper.equals("del")) {
            userService.delete(user);
        }
        return uid;
    }

    //文件上传
    @RequestMapping("uploadUser")
    public void uploadUser(MultipartFile headImg, String id, HttpServletRequest request) {
        //userService.uploadUser(headImg,id,request);    //上传本地
        // userService.uploadUserAliyun(headImg,id,request);  //上传到阿里云
        userService.uploadUserAliyuns(headImg, id, request);
    }

    @RequestMapping("showAll")
    @ResponseBody
    HashMap<String, Object> queryBuPager(Integer rows, Integer page) {
        HashMap<String, Object> map = userService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("sendPhoneCode")
    @ResponseBody
    public String sendPhoneCode(String phone) {
        //获取随机数
        String random = AliyunSendPhoneUtil.getRandom(6);
        System.out.println("存储验证码：" + random);
        //发送验证码
        String message = AliyunSendPhoneUtil.sendCode(phone, random);
        System.out.println(message);
        return message;
    }


    @RequestMapping("easyPoiUser")
    @ResponseBody
    public void easyPoiUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //阿里云导出
        response.addHeader("Access-Control-Allow-Origin", "*");
        List<User> users = userService.shouAll();
        for (User user : users) {
            byte[] image = HttpClientUtil.getImageFromNetByUrl(user.getHeadImg());
            user.setImage(image);
        }

        String fileName = "测试.xls";
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("应学App用户信息", "用户信息表"), User.class, users);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // HSSFWorkbook sheets = new HSSFWorkbook();
        //ServletOutputStream outputStream = response.getOutputStream();
        //String encode = URLEncoder.encode("用户表.xls", "UTF-8");
        //response.setHeader("content-disposition","attachment;filename=" + encode);
        //本地导出
        /*//创建日期格式对象
        HSSFDataFormat dataFormat = sheets.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //创建样式对象
        HSSFCellStyle cellStyle = sheets.createCellStyle();
        //设置好样式格式对象
        cellStyle.setDataFormat(format);*/
        /*List<User> users = userService.shouAll();

        String path = request.getSession().getServletContext().getRealPath("/upload/photo");
        System.out.println(path+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111");
        for (User user : users) {
            user.setHeadImg(path+"/"+user.getHeadImg());
        }

        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams("应学App用户信息","用户信息表"),User.class,users);
        //workbook.write(response.getOutputStream());
        workbook.write(new FileOutputStream(new File("F://easyPoi-user.xls")));*/
    }


    @RequestMapping("statistics")
    @ResponseBody
    public List<Integer> statistics(Integer time) {
        //本次测试的是添加所以把GoEasy插入到添加的方法中即可
        List<Integer> list = userService.selectByTime(time);
        return list;
    }

}
