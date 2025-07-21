package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.exception.ProductNotAvailableException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductDTO product) {
        logger.info(" Creating Product Name= {}, Price= {}", product.getName(), product.getPrice());
        if(product.isAvailable()){
            Product newProduct = new Product();
            newProduct.setName(product.getName());
            newProduct.setCategory(product.getCategory());
            newProduct.setPrice(product.getPrice());
            newProduct.setAvailable(product.isAvailable());
            return productRepository.save(newProduct);
        }else {
            throw new ProductNotAvailableException("Product must be marked as available to be saved.");
        }

    }

    public List<Product> getProducts() {
        logger.info("Fetching all products!");
        return productRepository.findAll();
    }

    public Product getProductById(long productId) {
        logger.info("Fetching Product By Id : {} ", productId);
        Optional<Product> optionalProduct = productRepository.findById((int) productId);
        if(!optionalProduct.isEmpty()){
            return optionalProduct.get();
        }else{
            throw new ProductNotFoundException(String.format("Product with Id %s Not Found", productId));
        }
    }

    public Product updateProduct(int productId, Product updatedProduct) {
        log.info("Updating product with Id : {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(updatedProduct.getName());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setAvailable(updatedProduct.isAvailable());
            return productRepository.save(product);
        }else{
            throw new ProductNotFoundException(String.format("Product with Id %s Not Found", productId));
        }
    }

    @Override
    public void deleteProduct(int productId) {
        log.info("Deleting Product with Id : {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            productRepository.delete(optionalProduct.get());
        }else{
            throw new ProductNotFoundException(String.format("Product with Id %s Not Found", productId));
        }
    }
}
