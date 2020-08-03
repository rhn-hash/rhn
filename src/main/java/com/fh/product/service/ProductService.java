package com.fh.product.service;

import com.fh.common.ServerResponse;
import com.fh.product.model.Product;

public interface ProductService {
    ServerResponse queryUrlProduct();

    ServerResponse queryProductList();

    Product selectProductById(Integer productId);

    Long updateStatus(Integer id, int count);

    ServerResponse queryListSrcProduct();
}
