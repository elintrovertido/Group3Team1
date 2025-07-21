package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    void testAllArgsConstructor() {
        ProductDTO productDTO = new ProductDTO(1L, "Laptop", 60000.0, Product.Category.ELECTRONICS, true);

        assertEquals(1L, productDTO.getProductId());
        assertEquals("Laptop", productDTO.getName());
        assertEquals(60000.0, productDTO.getPrice());
        assertEquals(Product.Category.ELECTRONICS, productDTO.getCategory());
        assertTrue(productDTO.isAvailable());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId(2L);
        productDTO.setName("Book");
        productDTO.setPrice(499.0);
        productDTO.setCategory(Product.Category.BOOKS);
        productDTO.setAvailable(false);

        assertEquals(2L, productDTO.getProductId());
        assertEquals("Book", productDTO.getName());
        assertEquals(499.0, productDTO.getPrice());
        assertEquals(Product.Category.BOOKS, productDTO.getCategory());
        assertFalse(productDTO.isAvailable());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductDTO dto1 = new ProductDTO(1L, "Phone", 30000.0, Product.Category.ELECTRONICS, true);
        ProductDTO dto2 = new ProductDTO(1L, "Phone", 30000.0, Product.Category.ELECTRONICS, true);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductDTO dto = new ProductDTO(3L, "Shirt", 1200.0, Product.Category.CLOTHING, true);

        String toString = dto.toString();
        assertTrue(toString.contains("Shirt"));
        assertTrue(toString.contains("1200.0"));
        assertTrue(toString.contains("CLOTHING"));
    }

    @Test
    void testCategoryEnumValues() {
        assertEquals("ELECTRONICS", ProductDTO.Category.ELECTRONICS.name());
        assertEquals("BOOKS", ProductDTO.Category.BOOKS.name());
        assertEquals("CLOTHING", ProductDTO.Category.CLOTHING.name());
        assertEquals("HOME", ProductDTO.Category.HOME.name());
    }
}
