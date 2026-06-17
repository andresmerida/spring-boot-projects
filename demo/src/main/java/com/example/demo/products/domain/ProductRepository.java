package com.example.demo.products.domain;

import java.util.List;
import java.util.Optional;


interface ProductRepository {
    List<ProductEntity> findByProductType(String productType);
    List<ProductEntity> findAll();
    ProductEntity save(ProductEntity productEntity);
    Optional<ProductEntity> findById(Long id);
}
