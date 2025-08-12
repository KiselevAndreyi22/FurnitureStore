package com.example.demo.dto.response;

import com.example.demo.model.Product;
import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private Double price;

    public ProductDto() {}

    public ProductDto(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

}
