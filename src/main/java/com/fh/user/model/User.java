package com.fh.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName("t_user")
public class User {

    @TableId(value = "userId",type = IdType.AUTO)
    private Integer   userId;  //用户Id

    @TableField("userName")
    private String  userName;   //账户名
    @TableField("userPassword")
    private String  userPassword;   //用户密码
    @TableField("userPhone")
    private String   userPhone; //手机号

    /*@TableField(exist = false)：表示该属性不为数据库表字段，但又是必须使用的。
    @TableField(exist = true)：表示该属性为数据库表字段。*/
    @TableField(exist = false)
    private String   code;      //验证码


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
