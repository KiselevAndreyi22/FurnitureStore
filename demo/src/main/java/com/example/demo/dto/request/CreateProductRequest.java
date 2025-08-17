package com.example.demo.dto.request;

import com.example.demo.model.ProductCategory;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private List<ProductCategory> categories;
}
