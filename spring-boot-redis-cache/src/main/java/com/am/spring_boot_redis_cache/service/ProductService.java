package com.am.spring_boot_redis_cache.service;

import com.am.spring_boot_redis_cache.dto.ProductDTO;
import com.am.spring_boot_redis_cache.dto.request.ProductRequest;
import com.am.spring_boot_redis_cache.entity.Product;
import com.am.spring_boot_redis_cache.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProductService {

    public static final String PRODUCT_CACHE = "products";
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @CachePut(value = PRODUCT_CACHE, key = "#result.id()")
    public ProductDTO create(ProductRequest productRequest) {
        Product newProduct = productRepository.save(matToEntity(productRequest));
        return mapToDTO(newProduct);
    }

    @Cacheable(value = PRODUCT_CACHE, key = "#result.id()")
    public Optional<ProductDTO> getProduct(Long id) {
        return productRepository.findById(id).map(this::mapToDTO);
    }

    @CachePut(value = PRODUCT_CACHE, key = "#productId")
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        if (!productId.equals(productDTO.id())) {
            throw new IllegalArgumentException("Product id does not match");
        }

        Product product = productRepository.findById(productId).orElseThrow(()
                -> new IllegalArgumentException("Resource not found for id: " + productId));
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        return mapToDTO(productRepository.save(product));
    }

    @CacheEvict(value = PRODUCT_CACHE, key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO mapToDTO(Product newProduct) {
        return new ProductDTO(newProduct.getId(), newProduct.getName(), newProduct.getPrice());
    }

    private Product matToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        return product;
    }
}
