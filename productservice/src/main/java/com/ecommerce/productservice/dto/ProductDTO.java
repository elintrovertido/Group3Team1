package com.ecommerce.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    public enum Category{
        ELECTRONICS,
        BOOKS,
        CLOTHING,
        HOME
    }

    private long productId;
    private String name;
    private double price;
    private com.ecommerce.productservice.model.Product.Category category;
    private boolean available;

}
