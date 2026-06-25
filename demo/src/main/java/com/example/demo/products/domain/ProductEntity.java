package com.example.demo.products.domain;

import java.math.BigDecimal;

class ProductEntity {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String productType;

    ProductEntity() {
    }

    ProductEntity(Long id, String name, String description, BigDecimal price, String productType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
