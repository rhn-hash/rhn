package com.fh.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.order.mapper.OrderInfoMapper;
import com.fh.order.mapper.OrderMapper;
import com.fh.order.model.Order;
import com.fh.order.model.OrderInfo;
import com.fh.order.service.OrderService;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import com.fh.user.model.User;
import com.fh.util.BigDecimalUtil;
import com.fh.util.IdUtil;
import com.fh.util.RedisUtil;
import com.fh.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ProductService productService;

    @Override
    public ServerResponse buildOrder(List<Cart> cartList, Integer payType, Integer addressId, User user) {
        String orderId = IdUtil.createId();
        List<OrderInfo> orderInfoList = new ArrayList<>();
        List<OrderInfo> orderInfoList1 = null;
        //商品总价格
        BigDecimal totalPrice = new BigDecimal("0.00");
        //库存不足的集合
        List<String> stockNotFull  = new ArrayList<>();

        for(Cart cart:cartList){
            Product product = productService.selectProductById(cart.getProductId());
            if(product.getStatus() >= cart.getCount()){
                //库存不足
                stockNotFull.add(cart.getName());
            }
               //减掉库存数量   判断库存是否充足
            Long res = productService.updateStatus(product.getId(),cart.getCount());
            if(res==1){
                OrderInfo orderInfo = buildOrderInfo(orderId, cart);
                orderInfoList.add(orderInfo);
                BigDecimal subTotal = BigDecimalUtil.mul(cart.getPrice().toString(),cart.getCount()+"");
                totalPrice = BigDecimalUtil.add(totalPrice,subTotal);
            }else{
                //库存不足
                stockNotFull.add(cart.getName());
            }
        }
        //生成订单 先判断是否有订单详情
        if(orderInfoList !=null && orderInfoList.size() == cartList.size()){
            //保存订单详细
            for(OrderInfo orderInfo:orderInfoList){
                orderInfoMapper.insert(orderInfo);
                //更新redis购物车
                updateRedisCart(user,orderInfo);
            }
            //库存都足  生成订单
            Order order = new Order();
            order.setCareatDate(new Date());
            order.setPayType(payType);
            order.setAddressId(addressId);
            order.setId(orderId);
            order.setTotalPrice(totalPrice);
            order.setStatus(SystemConstant.ORDER_STATIS_WAIT);
            orderMapper.insert(order);
            return ServerResponse.success(orderId);
        }else {
            return ServerResponse.error(stockNotFull);
        }
    }

    @Override
    public List<Order> queryOrderList() {
        return orderMapper.queryOrderList();
    }

    private void updateRedisCart(User user,OrderInfo orderInfo) {
        String cartJson = RedisUtil.hget(SystemConstant.CART_KEY + user.getUserId(), orderInfo.getProductId().toString());
        if(StringUtils.isNotBlank(cartJson)){
            Cart cart1 = JSONObject.parseObject(cartJson, Cart.class);
            if(cart1.getCount()<= orderInfo.getCount()){
                //删除购物车中该商品
                RedisUtil.hdel(SystemConstant.CART_KEY + user.getUserId(), orderInfo.getProductId().toString());
            }else {
                //更新购物车
                cart1.setCount((int) (cart1.getCount()- orderInfo.getCount()));
                String s = JSONObject.toJSONString(cart1);
                RedisUtil.hset(SystemConstant.CART_KEY + user.getUserId(), orderInfo.getProductId().toString(),s);

            }
        }
    }
    private void buildOrder(Integer payType, Integer addressId, User user, String orderId, BigDecimal totalPrice) {
        Order order = new Order();
        order.setCareatDate(new Date());
        order.setPayType(payType);
        order.setAddressId(addressId);
        order.setId(orderId);
        order.setTotalPrice(totalPrice);
        order.setUserId(user.getUserId());
        order.setStatus(SystemConstant.ORDER_STATIS_WAIT);
        orderMapper.insert(order);
    }

    private OrderInfo buildOrderInfo(String orderId, Cart cart) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setName(cart.getName());
        orderInfo.setFilePath(cart.getFilePath());
        orderInfo.setPrice(cart.getPrice());
        orderInfo.setOrderId(orderId);
        orderInfo.setProductId(cart.getProductId());
        orderInfo.setCount(cart.getCount());
        return orderInfo;
    }

}
