package com.example.demo.products.domain;

import com.example.demo.products.dto.ProductDto;
import com.example.demo.products.dto.ProductRequest;
import org.springframework.stereotype.Component;

@Component
class ProductMapper {

    ProductDto mapToProductDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getName(), productEntity.getDescription(), productEntity.getPrice(), productEntity.getProductType());
    }

    ProductEntity mapToProductEntity(ProductRequest request) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.name());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setProductType(request.productType());
        return productEntity;
    }
}
