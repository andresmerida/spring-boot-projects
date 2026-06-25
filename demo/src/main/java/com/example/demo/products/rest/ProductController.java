package com.example.demo.products.rest;

import com.example.demo.products.domain.ProductService;
import com.example.demo.products.dto.ProductDto;
import com.example.demo.products.dto.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search/{productType}")
    ResponseEntity<List<ProductDto>> getProductsByType(@PathVariable String productType) {
        return ResponseEntity.ok(productService.findByProductType(productType));
    }

    @GetMapping("/filter")
    ResponseEntity<List<ProductDto>> getProducts(@RequestParam(value = "productType", required = false) String productType) {
        return ResponseEntity.ok(
                productType == null ? productService.findAll() : productService.findByProductType(productType)
        );
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(value = "productType", required = false) String productType) {
        return ResponseEntity.ok(
                productType == null ? productService.findAll() : productService.findByProductType(productType)
        );
    }

    @PostMapping
    ResponseEntity<ProductDto> saveProduct(@RequestBody ProductRequest request) {
        ProductDto productDto = productService.save(request);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(productDto.id())
                        .toUri()
        ).body(productDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.edit(id, request));
    }
}
