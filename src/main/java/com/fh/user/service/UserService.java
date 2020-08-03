package com.fh.user.service;

import com.fh.common.ServerResponse;
import com.fh.user.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {


    void register(User user);

    ServerResponse checkUserByName(String userName);

    ServerResponse login(User user, HttpServletRequest request, HttpServletResponse response);

}
