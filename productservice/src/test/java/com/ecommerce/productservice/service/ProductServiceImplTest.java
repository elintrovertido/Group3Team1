package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.exception.ProductNotAvailableException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_WhenAvailable_ShouldSave() {
        ProductDTO dto = new ProductDTO(0, "Laptop", 999.99, Product.Category.ELECTRONICS , true);
        Product savedProduct = new Product(1, "Laptop", 999.99, Product.Category.ELECTRONICS, true);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(dto);

        assertThat(result.getName()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProduct_WhenNotAvailable_ShouldThrowException() {
        ProductDTO dto = new ProductDTO(0, "Book", 100.0, Product.Category.BOOKS, false);

        assertThrows(ProductNotAvailableException.class, () -> productService.createProduct(dto));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = List.of(
                new Product(1, "Book", 100.0, Product.Category.BOOKS, true),
                new Product(2, "TV", 1500.0, Product.Category.ELECTRONICS, true)
        );

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getProducts();

        assertThat(result.size()).isEqualTo(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_WhenExists_ShouldReturnProduct() {
        Product product = new Product(1, "Book", 100.0, Product.Category.BOOKS, true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1);

        assertThat(result.getName()).isEqualTo("Book");
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void testGetProductById_WhenNotFound_ShouldThrowException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1));
    }

    @Test
    void testUpdateProduct_WhenExists_ShouldUpdate() {
        Product existing = new Product(1, "Book", 100.0, Product.Category.BOOKS, true);
        Product updated = new Product(1, "Notebook", 120.0, Product.Category.BOOKS, true);

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(updated);

        Product result = productService.updateProduct(1, updated);

        assertThat(result.getName()).isEqualTo("Notebook");
        verify(productRepository, times(1)).save(existing);
    }

    @Test
    void testUpdateProduct_WhenNotFound_ShouldThrowException() {
        Product updated = new Product(1, "Notebook", 120.0, Product.Category.BOOKS, true);

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1, updated));
    }

    @Test
    void testDeleteProduct_WhenExists_ShouldDelete() {
        Product product = new Product(1, "Phone", 300.0, Product.Category.ELECTRONICS, true);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(1);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_WhenNotFound_ShouldThrowException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1));
    }
}
