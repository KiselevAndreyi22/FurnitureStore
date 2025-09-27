package com.example.demo.service;

import com.example.demo.dto.response.CartDto;
import com.example.demo.exception.ProductInCartNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart putProduct(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteById(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ProductInCartNotFoundException();
        }
        cartRepository.deleteById(id);
    }

    public List<CartDto> getAllProducts() {
        List<Cart> products = cartRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CartDto toDTO(Cart cart) {
        //UserDto userDto = new UserDto(cart.getCreateUser());
        return new CartDto(cart);
    }
}
