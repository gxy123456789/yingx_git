package com.baizhi.gxy.serviceImpl;

import com.baizhi.gxy.annotation.AddCache;
import com.baizhi.gxy.annotation.AddLog;
import com.baizhi.gxy.annotation.DelCahe;
import com.baizhi.gxy.dao.CategoryMapper;
import com.baizhi.gxy.entity.Category;
import com.baizhi.gxy.entity.CategoryExample;
import com.baizhi.gxy.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @AddCache
    @Override
    public HashMap<String, Object> CategoryqueryByPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdIsNull();
        //查询二级的
        //example.createCriteria().andParentIdEqualTo(id);
        //example.createCriteria().andLevelsNotEqualTo("zs").andParentIdIsNotNull()
        Integer records = categoryMapper.selectCountByExample(example);
        map.put("records", records);
        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页   page
        map.put("page", page);
        //数据     rows
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdIsNull();
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);
        map.put("rows", categories);

        return map;
    }

    @AddCache
    @Override
    public HashMap<String, Object> CategoryqueryByPageTWO(Integer page, Integer rows, String parent_id) {
        HashMap<String, Object> map = new HashMap<>();
        //封装数据
        //总条数   records
        CategoryExample example = new CategoryExample();
        //example.createCriteria().andParentIdIsNull();
        //查询二级的
        example.createCriteria().andParentIdEqualTo(parent_id);
        //example.createCriteria().andLevelsNotEqualTo("zs").andParentIdIsNotNull()
        Integer records = categoryMapper.selectCountByExample(example);
        map.put("records", records);
        //总页数   total   总条数/每页展示条数  是否有余数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页   page
        map.put("page", page);
        //数据     rows
        //参数  忽略条数,获取几条
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(parent_id);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);
        map.put("rows", categories);
        return map;
    }

    @DelCahe
    @AddLog(value = "删除类别")
    @Override
    public HashMap<String, Object> delete(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //根据类别对象查询类别信息   id
        Category cate = categoryMapper.selectOne(category);
        //判断删除的是一级类别还是二级类别
        if (cate.getLevels() == 1) {
            //一级类别  判断是否有二级类别   二级类别数量
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(example);
            if (count == 0) {
                //没有   直接删除
                categoryMapper.deleteByPrimaryKey(category);
                map.put("status", "200");
                map.put("message", "删除成功");
            } else {
                //有二级类别   返回提示信息  不能删除
                map.put("status", "400");
                map.put("message", "删除失败，该类别下有子类别");
            }
        } else {
            //二级类别  是否有视频
            //有   不能删除  提示信息
            //没有 直接删除
            categoryMapper.deleteByPrimaryKey(category);
            map.put("status", "200");
            map.put("message", "删除成功");
        }
        return map;
    }

    @DelCahe
    @AddLog(value = "添加类别")
    @Override
    public void add(Category category) {
        ///添加的时一级类别还是二级类别
        if (category.getParent_id() == null) {
            //添加的时一级类别
            category.setLevels(1);
        } else {
            category.setLevels(2);
        }

        category.setId(UUID.randomUUID().toString());
        //执行添加
        categoryMapper.insertSelective(category);
    }

    @DelCahe
    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
