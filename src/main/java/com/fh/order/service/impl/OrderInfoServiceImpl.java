package com.fh.order.service.impl;

import com.fh.order.mapper.OrderInfoMapper;
import com.fh.order.model.OrderInfo;
import com.fh.order.service.OrderInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> queryList() {
        return orderInfoMapper.queryList();
    }
}
