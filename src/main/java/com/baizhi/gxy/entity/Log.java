package com.baizhi.gxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id
    private String id;
    @Column(name = "admin_name")
    private String adminName;
    @JsonFormat(pattern = "yyyy-MM-dd") //返回json时json的数据格式      出参
    @DateTimeFormat(pattern = "yyyy-MM-dd")  //收集参数时指定日期格式    入参
    private Date data;
    private String operation;
    private String status;
}
