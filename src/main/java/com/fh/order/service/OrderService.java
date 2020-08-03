package com.fh.order.service;

import com.fh.cart.model.Cart;
import com.fh.common.ServerResponse;
import com.fh.order.model.Order;
import com.fh.user.model.User;

import java.util.List;

public interface OrderService {
    ServerResponse buildOrder(List<Cart> cartList, Integer payType, Integer addressId, User user);

    List<Order> queryOrderList();
}
