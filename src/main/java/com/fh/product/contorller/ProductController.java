package com.fh.product.contorller;

import com.fh.common.Ignore;
import com.fh.common.ServerResponse;
import com.fh.product.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("productController")
public class ProductController {
    @Resource
    private ProductService productService;


    //查询走马灯的图片
    @RequestMapping("queryUrlProduct")
    @Ignore
    public ServerResponse queryUrlProduct(){
        return productService.queryUrlProduct();
    }
    //查询走马灯的图片
    @RequestMapping("queryListSrcProduct")
    @Ignore
    public ServerResponse queryListSrcProduct(){
        return productService.queryListSrcProduct();
    }

    //查询下滑走马灯的图片
    @RequestMapping("queryProductList")
    @Ignore
    public ServerResponse queryProductList(){
        return productService.queryProductList();
    }

}
