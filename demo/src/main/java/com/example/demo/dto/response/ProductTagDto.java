package com.example.demo.dto.response;

import com.example.demo.model.ProductTag;
import lombok.Data;

@Data
public class ProductTagDto {
    private String name;

    public ProductTagDto(ProductTag productTag) {

        this.name = productTag.getName();
    }
}
