package com.fh.logininterceptors;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import com.fh.common.Ignore;
import com.fh.common.LoginException;
import com.fh.user.model.User;
import com.fh.util.JwtTokenUtils;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //处理客户端传过来的自定义头信息
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-auth,mtoken,content-type");
        // 处理客户端发过来put,delete
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "PUT,POST,DELETE,GET");
        HandlerMethod methodHandle = (HandlerMethod) handler;
        Method method = methodHandle.getMethod();
        //判断是否存在 @Ignore注解  如果存在  该方法不拦截
        if(method.isAnnotationPresent(Ignore.class)){
          return true;
        }
        //获取token的值
        String token = request.getHeader("x-auth");
        //如果没有token返回登录页面
        if (StringUtils.isEmpty(token)){
            throw  new LoginException();
        }
        //是否token有值 // 是否已失效
        boolean exist = RedisUtil.exists1(SystemConstant.TOKEN_KEY+token);
        if (!exist){
            throw  new LoginException();
        } /*else {
            token = RedisUtil.get(token);
        }*/
        //验证token
        boolean res = JwtTokenUtils.verify(token);
        if (res){
            String encode = URLDecoder.decode(JwtTokenUtils.getuser(token),"utf-8");
            User userDB= JSONObject.parseObject(encode, User.class);
            request.getSession().setAttribute(SystemConstant.SESSION_KEY,userDB);
            request.getSession().setAttribute(SystemConstant.TOKEN_KEY,token);
            //续命
            /*RedisUtil.setEx(SystemConstant.TOKEN_KEY + token,token,SystemConstant.TOKEN_EXPIRE_TIME);
            System.out.println(userDB.toString());*/
        }else {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

