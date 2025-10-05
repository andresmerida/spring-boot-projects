package com.am.spring_boot_redis_cache;

import com.am.spring_boot_redis_cache.dto.ProductDTO;
import com.am.spring_boot_redis_cache.entity.Product;
import com.am.spring_boot_redis_cache.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.am.spring_boot_redis_cache.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class SpringBootRedisCacheApplicationTest {
    @Container
    @ServiceConnection
    static GenericContainer redisContainer = new GenericContainer(DockerImageName.parse("redis:8.2.2"))
            .withExposedPorts(6379);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CacheManager cacheManager;

    @MockitoSpyBean
    private ProductRepository productRepositorySpy;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        productRepository.deleteAll(); // Ensure a clean database for each test
    }

    @Test
    void testCreateProductAndCacheIt() throws Exception {
        ProductDTO productDto = new ProductDTO(null, "Laptop", BigDecimal.valueOf(1200L));

        // Step 1: Create a Product
        MvcResult result = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andReturn();


        ProductDTO createdProduct = objectMapper.readValue(result.getResponse().getContentAsString(), ProductDTO.class);
        Long productId = createdProduct.id();

        // Step 2: Check Product Exists in DB
        Assertions.assertTrue(productRepository.findById(productId).isPresent());

        // Step 3: Check Cache
        Cache cache = cacheManager.getCache(ProductService.PRODUCT_CACHE);
        assertNotNull(cache);
        assertNotNull(cache.get(productId, ProductDTO.class));
    }

    @Test
    void testGetProductAndVerifyCache() throws Exception {
        // Step 1: Save product in DB
        Product product = new Product();
        product.setName("Phone");
        product.setPrice(BigDecimal.valueOf(800L));
        product = productRepository.saveAndFlush(product);

        // Step 2: Fetch product
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{productId}",  product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"));

        Mockito.verify(productRepositorySpy, Mockito.times(1)).findById(product.getId());

        Mockito.clearInvocations(productRepositorySpy);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{productId}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Phone"));

        Mockito.verify(productRepositorySpy, Mockito.times(0)).findById(product.getId());
    }

    @Test
    void testUpdateProductAndVerifyCache() throws Exception {
        // Step 1: Create and Save Product
        Product product = new Product();
        product.setName("Tablet");
        product.setPrice(BigDecimal.valueOf(500L));
        product = productRepository.save(product);

        ProductDTO updatedProductDto = new ProductDTO(product.getId(), "Updated Tablet", BigDecimal.valueOf(550L));

        // Step 2: Update Product
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Tablet"))
                .andExpect(jsonPath("$.price").value(550.0));

        // Step 3: Verify Cache is Updated
        Cache cache = cacheManager.getCache(ProductService.PRODUCT_CACHE);
        assertNotNull(cache);
        ProductDTO cachedProduct = cache.get(product.getId(), ProductDTO.class);
        assertNotNull(cachedProduct);
        Assertions.assertEquals("Updated Tablet", cachedProduct.name());
    }

    @Test
    void testDeleteProductAndEvictCache() throws Exception {
        // Step 1: Create and Save Product
        Product product = new Product();
        product.setName("Smartwatch");
        product.setPrice(BigDecimal.valueOf(250L));
        product = productRepository.save(product);

        // Step 2: Delete Product
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/" + product.getId()))
                .andExpect(status().isNoContent());

        // Step 3: Check that Product is Deleted from DB
        Assertions.assertFalse(productRepository.findById(product.getId()).isPresent());

        // Step 4: Check Cache Eviction
        Cache cache = cacheManager.getCache(ProductService.PRODUCT_CACHE);
        assertNotNull(cache);
        Assertions.assertNull(cache.get(product.getId()));
    }
}