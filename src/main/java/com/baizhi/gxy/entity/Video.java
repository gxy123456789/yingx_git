package com.baizhi.gxy.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Document(indexName = "yingxv", type = "video")
@Table(name = "yx_video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String brief;
    @Field(type = FieldType.Keyword)
    private String path;
    @Field(type = FieldType.Keyword)
    private String cover;
    @Field(type = FieldType.Date)
    @JsonFormat(pattern = "yyyy-MM-dd") //返回json时json的数据格式      出参
    @DateTimeFormat(pattern = "yyyy-MM-dd")  //收集参数时指定日期格式    入参
    @Column(name = "publish_date")
    private Date publishDate;
    @Field(type = FieldType.Keyword)
    @Column(name = "category_id")
    private String categoryId;
    @Field(type = FieldType.Keyword)
    @Column(name = "group_id")
    private String groupId;
    @Field(type = FieldType.Keyword)
    @Column(name = "user_id")
    private String userId;

}