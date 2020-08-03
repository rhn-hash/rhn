package com.fh.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@TableName("t_product")
public class Product {

    private Integer    id;
    private String     name;
    private Integer    brandId;
    private BigDecimal price;
    private Integer    status;
    private String     filePath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date       createDate;
    private Integer    category1;
    private Integer    category2;
    private Integer    category3;
    private Integer    isht;

    public Integer getIsht() {
        return isht;
    }

    public void setIsht(Integer isht) {
        this.isht = isht;
    }

    public Product() {
    }

    public Product(Integer id, String name, Integer brandId, BigDecimal price, Integer status, String filePath, Date createDate, Integer category1, Integer category2, Integer category3,Integer isht) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
        this.price = price;
        this.status = status;
        this.filePath = filePath;
        this.createDate = createDate;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.isht = isht;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCategory1() {
        return category1;
    }

    public void setCategory1(Integer category1) {
        this.category1 = category1;
    }

    public Integer getCategory2() {
        return category2;
    }

    public void setCategory2(Integer category2) {
        this.category2 = category2;
    }

    public Integer getCategory3() {
        return category3;
    }

    public void setCategory3(Integer category3) {
        this.category3 = category3;
    }
}
