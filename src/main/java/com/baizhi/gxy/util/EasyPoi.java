package com.baizhi.gxy.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.gxy.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;

public class EasyPoi {

    public static void esayPoiUser() {

        ArrayList<User> users = new ArrayList<>();
        User user = new User();
        users.add(user);
        ExportParams exportParams = new ExportParams("应学App用户信息", "用户信息表");

        Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, User.class, users);

        try {
            workbook.write(new FileOutputStream(new File("F://easyPoi-user.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ImageToBase64(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static boolean Base64ToImage(String base64, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        if (base64 == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
