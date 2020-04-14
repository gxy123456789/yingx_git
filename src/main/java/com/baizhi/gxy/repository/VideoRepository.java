package com.baizhi.gxy.repository;

import com.baizhi.gxy.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//泛型   <操作对象类型,序列化主键的类型>
public interface VideoRepository extends ElasticsearchRepository<Video, String> {

}
