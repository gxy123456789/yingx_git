package com.baizhi.gxy.service;


import com.baizhi.gxy.entity.Video;
import com.baizhi.gxy.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface VideoService {

    HashMap<String, Object> queryByPage(Integer page, Integer rows);

    String add(Video video);

    void update(Video video);

    HashMap<String, Object> delete(Video video);

    void uploadVdieo(MultipartFile path, String id, HttpServletRequest request);

    List<VideoVo> queryByReleaseTime();

    //检索
    List<Video> querySearch(String content);

    List<Video> querySearchs(String content);
}
