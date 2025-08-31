package com.example.demo.dto.response;

import com.example.demo.model.Cart;
import lombok.Data;

@Data
public class CartDto {
    private ProductDto product;

    public CartDto(Cart cart) {
        this.product = new ProductDto(cart.getProduct());
    }
}
