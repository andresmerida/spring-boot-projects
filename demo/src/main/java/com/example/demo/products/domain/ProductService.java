package com.example.demo.products.domain;

import com.example.demo.products.dto.ProductDto;
import com.example.demo.products.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> findByProductType(String productType) {
        return productRepository.findByProductType(productType)
                .stream()
                .map(productMapper::mapToProductDto)
                .toList();
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToProductDto)
                .toList();
    }

    public ProductDto save(ProductRequest request) {
        ProductEntity productEntity = productMapper.mapToProductEntity(request);
        return productMapper.mapToProductDto(productRepository.save(productEntity));
    }

    public ProductDto edit(Long id, ProductRequest request) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productEntity.setName(request.name());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setProductType(request.productType());

        return productMapper.mapToProductDto(productRepository.save(productEntity));
    }
}
