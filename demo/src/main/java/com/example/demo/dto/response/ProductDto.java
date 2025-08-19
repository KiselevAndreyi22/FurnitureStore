package com.example.demo.dto.response;

import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private ProductCategory category;
    private List<ProductTagDto> tags;


    public ProductDto() {}

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory();
        this.tags = product.getTags()
                .stream()
                .map(ProductTagDto::new)
                .toList();
    }

}
