package com.baizhi.gxy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Excel(name = "ID")
    @Id
    private String id;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "手机号")
    private String phone;

    @Column(name = "head_img")
    private String headImg;
    @Excel(name = "签名")
    private String sign;
    @Excel(name = "微信")
    private String wechat;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "注册时间")
    @Column(name = "create_date")
    @JsonFormat(pattern = "yyyy-MM-dd") //返回json时json的数据格式      出参
    @DateTimeFormat(pattern = "yyyy-MM-dd")  //收集参数时指定日期格式    入参
    private Date createDate;

    @Transient
    @Excel(name = "头像", type = 2, imageType = 2)
    private byte[] image;

    private String sex;
    private String city;
}