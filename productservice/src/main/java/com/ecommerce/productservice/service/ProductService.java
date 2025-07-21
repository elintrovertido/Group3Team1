package com.ecommerce.productservice.service;


import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO product);

    List<Product> getProducts();

    Product getProductById(long productId);

    Product updateProduct(int productId, Product product);

    void deleteProduct(int productId);
}
