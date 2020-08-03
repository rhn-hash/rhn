package com.fh.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ServerResponse;
import com.fh.product.mapper.ProductMapper;
import com.fh.product.model.Product;
import com.fh.product.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;


    @Override
    public ServerResponse queryUrlProduct() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isht",0);
        List<Product> products = productMapper.selectList(queryWrapper);
        return ServerResponse.success(products);
    }


    @Override
    public ServerResponse queryProductList() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isht",2);
        List<Product> products = productMapper.selectList(queryWrapper);
        return ServerResponse.success(products);
    }

    @Override
    public Product selectProductById(Integer productId) {
        Product product = productMapper.selectById(productId);
        return product;
    }


    @Override
    public Long updateStatus(Integer id, int count) {
        return productMapper.updateStatus(id,count);
    }

    @Override
    public ServerResponse queryListSrcProduct() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isht",1);
        List<Product> products = productMapper.selectList(queryWrapper);
        return ServerResponse.success(products);
    }
}
