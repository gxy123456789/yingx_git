package com.baizhi.gxy;

import com.baizhi.gxy.entity.Emp;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class easyPoi {

    @Test
    public void testpois() {
        //创建一个Excel文档
        HSSFWorkbook sheets = new HSSFWorkbook();

        //创建一个工作薄 参数：工作薄名字(sheet1,shet2....)
        HSSFSheet sheet = sheets.createSheet("用户表");

        //创建一行 参数：行下标(下标从0开始)
        HSSFRow row = sheet.createRow(0);

        //创建一个单元格 参数：单元格下标(下标从0开始)
        HSSFCell cell = row.createCell(0);

        //给单元格设置内容
        cell.setCellValue("这是第一行第一个单元格");

        //导出单元格
        try {
            sheets.write(new FileOutputStream(new File("F://TestPoi.xls")));
            //释放资源
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testpoistExportUser() {

        //创建集合
        Emp user1 = new Emp("1", "小黑", 12, new Date());
        Emp user2 = new Emp("2", "小红", 26, new Date());
        Emp user3 = new Emp("3", "小绿", 23, new Date());
        Emp user4 = new Emp("4", "小紫", 17, new Date());
        Emp user5 = new Emp("5", "小蓝", 31, new Date());
        Emp user6 = new Emp("6", "小黄", 18, new Date());
        List<Emp> users = Arrays.asList(user1, user2, user3, user4, user5, user6);

        //创建一个Excel文档
        HSSFWorkbook sheets = new HSSFWorkbook();

        //创建一个工作薄 参数：工作薄名字(sheet1,shet2....)
        HSSFSheet sheet = sheets.createSheet("用户表1");

        //设置列宽   单位1/256
        sheet.setColumnWidth(3, 20 * 256);

        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title = {"ID", "名字", "年龄", "生日"};

        //设置行高  单位1/20
        row.setHeight((short) (20 * 20));

        //处理单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i); //单元格下标
            cell.setCellValue(title[i]); //单元格内容
        }

        //创建日期格式对象
        HSSFDataFormat dataFormat = sheets.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");

        //创建样式对象
        HSSFCellStyle cellStyle = sheets.createCellStyle();
        //设置好样式格式对象
        cellStyle.setDataFormat(format);

        //处理数据行
        for (int i = 0; i < users.size(); i++) {
            //遍历一次创建一行
            HSSFRow row2 = sheet.createRow(i + 1);
            //每行对应放的数据
            row2.createCell(0).setCellValue(users.get(i).getId());
            row2.createCell(1).setCellValue(users.get(i).getName());
            row2.createCell(2).setCellValue(users.get(i).getAge());

            row2.createCell(3).setCellValue(users.get(i).getDate());
            //设置单元格日期格式
            Cell cell2 = row2.createCell(3);
            cell2.setCellStyle(cellStyle); //添加日期样式
            cell2.setCellValue(users.get(i).getDate()); //添加数据
        }

        //导出单元格
        try {
            sheets.write(new FileOutputStream(new File("F://测试TestPoi.xls")));
            //释放资源
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
