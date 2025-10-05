package com.am.spring_boot_redis_cache.controller;

import com.am.spring_boot_redis_cache.dto.ProductDTO;
import com.am.spring_boot_redis_cache.dto.request.ProductRequest;
import com.am.spring_boot_redis_cache.service.ProductService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(
                productService.create(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productId") Long productId) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok()
                .body(
                    productService.getProduct(productId).orElseThrow(
                            () -> new IllegalArgumentException("Product not found with id: " + productId)
                    )
                );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,
                                                    @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok()
                .body(
                        productService.updateProduct(productId, productDTO)
                );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
