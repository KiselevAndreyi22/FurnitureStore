package com.example.demo.dto.request;

import com.example.demo.model.ProductTag;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private List<ProductTag> tags;
}
