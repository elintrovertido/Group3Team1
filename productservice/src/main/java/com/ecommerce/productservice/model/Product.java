package com.ecommerce.productservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    public enum Category{
        ELECTRONICS,
        BOOKS,
        CLOTHING,
        HOME
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 1, message = "Price has to be greater than 0")
    private double price;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category is required!")
    private Category category;
    @NotNull(message = "Availability is required!")
    private boolean available;

}
