package com.fh.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.common.Idempotent;
import com.fh.common.ServerResponse;
import com.fh.common.UserAnnotation;
import com.fh.order.model.Order;
import com.fh.order.service.OrderService;
import com.fh.user.model.User;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orderController")
public class OrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping("buildOrder")
    @Idempotent
    public ServerResponse buildOrder(Integer payType, Integer addressId,String listStr,@UserAnnotation User User){
        List<Cart> cartList = new ArrayList<>();
        if(StringUtils.isNotBlank(listStr)){
             cartList = JSONObject.parseArray(listStr, Cart.class);
        }else{
            return ServerResponse.error("请选择商品");
        }
        return orderService.buildOrder(cartList,payType,addressId,User);
    }

    @RequestMapping("queryOrderList")
    public List<Order> queryOrderList(){
        List<Order> orderList = orderService.queryOrderList();
        return orderList;
    }

    //验证幂等性
    @RequestMapping("getToken")
    public ServerResponse getToken(){
        String mtoken = UUID.randomUUID().toString();
        RedisUtil.set(mtoken,mtoken);
        return ServerResponse.success(mtoken);
    }
}
