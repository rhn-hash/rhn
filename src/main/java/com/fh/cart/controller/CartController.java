package com.fh.cart.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.cart.service.CartService;
import com.fh.common.ServerEnum;
import com.fh.common.ServerResponse;
import com.fh.common.UserAnnotation;
import com.fh.user.model.User;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("cartController")
public class CartController {
    @Autowired
    private CartService cartService;

    //添加商品到购物车
    @RequestMapping("buy")
    public ServerResponse buy(@UserAnnotation User user,Integer productId, Integer count){
        return cartService.buy(productId,count,user);
    }
    //查询加入购物车商品的数量
    @RequestMapping("queryCartProductCount")
    public ServerResponse queryCartProductCount(@UserAnnotation User user){
        //从redis 中的 hvals 获取数据
        List<String> stringList = RedisUtil.hget(SystemConstant.CART_KEY + user.getUserId());
        long cartCount=0;
        //判断不能为空 不能小于0
        if (stringList != null && stringList.size()>0){
            for (String str : stringList) {
                //把json串对象
                Cart cart = JSONObject.parseObject(str, Cart.class);
                //进行拼接
                cartCount+=cart.getCount();
            }
            return ServerResponse.success(cartCount);
        }else {
            return ServerResponse.success(0);
        }
    }


    //查询购物车是否为空
    @RequestMapping("queryList")
    public ServerResponse queryList(@UserAnnotation User user){
        List<String> stringList = RedisUtil.hget(SystemConstant.CART_KEY + user.getUserId());
        List<Cart> cartList = new ArrayList<>();
        if(stringList!=null && stringList.size()> 0 ){
            for (String str : stringList){
                Cart cart = JSONObject.parseObject(str, Cart.class);
                cartList.add(cart);
            }
        }else {
            return ServerResponse.error(ServerEnum.CART_IS_NULL.getMsg());
        }
        return ServerResponse.success(cartList);
    }
    //单个删除商品
    @RequestMapping("delProductCart")
    public ServerResponse delProductCart(Integer productId,@UserAnnotation User user){
        RedisUtil.hdel(SystemConstant.CART_KEY+user.getUserId(),productId.toString());
        return ServerResponse.success();
    }
    //批量删除商品
    @RequestMapping("delBatch")
    public ServerResponse delBatch(@RequestParam("") List list,@UserAnnotation User user){
        for(int i = 0;i<list.size();i++){
            RedisUtil.hdel(SystemConstant.CART_KEY+user.getUserId(),list.get(i).toString());
        }
        return ServerResponse.success();
    }
}
