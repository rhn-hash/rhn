package com.fh.user.contorller;


import com.aliyuncs.exceptions.ClientException;
import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.user.model.User;
import com.fh.user.service.UserService;
import com.fh.util.AliyunSmsUtils;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("userController")
@Api(value="用户管理",tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;



    //根据Name查询数据库是否存在相同的Name
    @ApiOperation(value="获取用户列表",tags = "用户列表")
    @RequestMapping(value="checkUserByName",method= RequestMethod.GET)
    @Ignore
    public ServerResponse checkUserByName(String userName){
        ServerResponse serverResponse = userService.checkUserByName(userName);
        return serverResponse;
    }


    //新增用户
    @RequestMapping("register")
    @ApiOperation(value="新增用户",tags = "新增用户")
    @Ignore
    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();
        try {
            userService.register(user);
            map.put("code",200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",false);
        }
        return map;
    }


    //获取验证码
    @RequestMapping("getCode")
    @Ignore
    private Map<String,Object> getCode(String userPhone) throws ClientException {
        Map<String,Object> map = new HashMap<>();
        try {
            String code = String.valueOf((int)((Math.random()*9+1)*100000));
            AliyunSmsUtils.sendSms(userPhone,code);
            map.put("code",200);
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("code",201);
        }
        return map;
    }

    //登录
    @RequestMapping("login")
    @Ignore
    public ServerResponse login(User user, HttpServletRequest request, HttpServletResponse response) {
        return userService.login(user,request,response);
    }

    //退出
    @RequestMapping("out")
    @Ignore
    public ServerResponse out(HttpServletRequest request){
        //让token失效
        String token = (String) request.getSession().getAttribute(SystemConstant.TOKEN_KEY);
        RedisUtil.del(SystemConstant.TOKEN_KEY + token);
        return ServerResponse.success();
    }
    //弹框登录
    @RequestMapping("checkLogin")
    @Ignore
    public ServerResponse checkLogin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(SystemConstant.SESSION_KEY);
        if(user == null){
            return ServerResponse.error();
        }
        return ServerResponse.success();
    }
}
