package com.fh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.user.mapper.UserMapper;
import com.fh.user.model.User;
import com.fh.user.service.UserService;
import com.fh.util.JwtTokenUtils;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public ServerResponse checkUserByName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName",userName);
        User user =  userMapper.selectOne(wrapper);
        if(user == null){
            //数据库不存在该账号  可以注册
            return  ServerResponse.success();
        }
        return ServerResponse.error();
    }


    @Override
    public void register(User user) {
        /*//生成一个字母加数字 组成的20位 随机字符串
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String encodePassword =  Md5Util.md5(Md5Util.md5(user.getUserPassword()+salt));
        user.setUserPassword(encodePassword);*/
        userMapper.register(user);
    }

    @Override
    public ServerResponse login(User user, HttpServletRequest request, HttpServletResponse response) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("userName",user.getUserName());
        wrapper.or();
        wrapper.eq("userPhone",user.getUserName());
        User userDB =  userMapper.selectOne(wrapper);
        if(userDB==null){
            //没有该账户
            return ServerResponse.error("账号或手机号不存在");
        }
        //判断密码是否正确
        if(!user.getUserPassword().equals(userDB.getUserPassword())){
            //密码错误
            return ServerResponse.error("密码错误");
        }
        //账号密码正确,生成token，返回前台
        String token ="";
        try {
            String jsonString = JSONObject.toJSONString(userDB);
            String encodeJson = URLEncoder.encode(jsonString, "utf-8");
            token = JwtTokenUtils.sign(encodeJson);
            RedisUtil.setEx(SystemConstant.TOKEN_KEY+token,token, SystemConstant.TOKEN_EXPIRE_TIME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ServerResponse.success(token);
    }



}
