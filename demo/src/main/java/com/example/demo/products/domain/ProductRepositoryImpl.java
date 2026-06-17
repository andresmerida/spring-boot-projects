package com.example.demo.products.domain;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class ProductRepositoryImpl implements ProductRepository {

    private final ConcurrentMap<Long, ProductEntity> productsStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(6);

    ProductRepositoryImpl() {
        productsStore.put(1L, new ProductEntity(1L, "Laptop", "High-performance laptop", BigDecimal.valueOf(1200.00), "electronics"));
        productsStore.put(2L, new ProductEntity(2L, "Phone", "Smartphone with OLED display", BigDecimal.valueOf(800.00), "electronics"));
        productsStore.put(3L, new ProductEntity(3L, "Desk Chair", "Ergonomic office chair", BigDecimal.valueOf(250.00), "furniture"));
        productsStore.put(4L, new ProductEntity(4L, "Coffee Maker", "Automatic drip coffee maker", BigDecimal.valueOf(90.0), "appliances"));
        productsStore.put(5L, new ProductEntity(5L, "Notebook", "College-ruled paper notebook", BigDecimal.valueOf(5.000), "stationery"));
    }



    @Override
    public List<ProductEntity> findByProductType(String productType) {
        return productsStore.values().stream()
                .filter(product -> product.getProductType().equals(productType))
                .toList();
    }

    @Override
    public List<ProductEntity> findAll() {
        return productsStore.values().stream().toList();
    }

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        if (productEntity.getId() == null) {
            productEntity.setId(idGenerator.getAndIncrement());
        }
        productsStore.put(productEntity.getId(), productEntity);

        return productEntity;
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return Optional.ofNullable(productsStore.get(id));
    }
}
