package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO(1, "Book", 199.0, Product.Category.BOOKS, true);
        Product savedProduct = new Product(1, "Book", 199.0, Product.Category.BOOKS, true);

        Mockito.when(productService.createProduct(any(ProductDTO.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Book"))
                .andExpect(jsonPath("$.price").value(199.0));
    }

    @Test
    void testGetProducts() throws Exception {
        List<Product> products = List.of(
                new Product(1, "Book", 100.0, Product.Category.BOOKS, true),
                new Product(2, "Phone", 500.0, Product.Category.ELECTRONICS, true)
        );

        Mockito.when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product(1, "Book", 100.0, Product.Category.BOOKS, true);
        Mockito.when(productService.getProductById(1)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product(1, "Updated Book", 150.0, Product.Category.BOOKS, true);
        Mockito.when(productService.updateProduct(Mockito.eq(1), any(Product.class)))
                .thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Book"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with Id 1, Deleted"));
    }
}
