package com.fh.cart.service;

import com.fh.common.ServerResponse;
import com.fh.common.UserAnnotation;
import com.fh.user.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    ServerResponse buy(Integer productId, Integer count, @UserAnnotation User user);
}
