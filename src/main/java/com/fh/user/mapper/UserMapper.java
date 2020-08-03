package com.fh.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.user.model.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {


    void register(User user);

}
