package com.baizhi.gxy.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "yingxs", type = "emp")
public class Emp {

    @Id
    @Excel(name = "ID", width = 20, height = 20)
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @Excel(name = "名字")
    private String name;

    @Field(type = FieldType.Integer)
    @Excel(name = "年龄")
    private Integer age;

    @Field(type = FieldType.Date)
    @Excel(name = "生日", format = "yyyy-MM-dd")
    private Date date;

}
