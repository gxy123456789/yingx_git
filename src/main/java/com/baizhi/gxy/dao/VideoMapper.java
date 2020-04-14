package com.baizhi.gxy.dao;

import com.baizhi.gxy.entity.Video;
import com.baizhi.gxy.po.VideoPo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {

    List<VideoPo> queryByReleaseTime();
}