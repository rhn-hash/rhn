package com.fh.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.category.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select * from t_category")
    List<Map<String, Object>> queryList();
}
