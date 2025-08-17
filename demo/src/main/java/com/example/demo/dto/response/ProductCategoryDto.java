package com.example.demo.dto.response;

import com.example.demo.model.ProductCategory;
import lombok.Data;

@Data
public class ProductCategoryDto {
    private String name;

    public ProductCategoryDto(ProductCategory productCategory) {

        this.name = productCategory.getName();
    }
}
