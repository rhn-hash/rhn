package com.fh.order.controller;

import com.fh.common.Ignore;
import com.fh.order.model.OrderInfo;
import com.fh.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orderInfoController")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;


    @RequestMapping("queryList")
    @Ignore
    public List<OrderInfo> queryList(){
        List<OrderInfo> orderInfoList = orderInfoService.queryList();
        return orderInfoList;
    }
}
