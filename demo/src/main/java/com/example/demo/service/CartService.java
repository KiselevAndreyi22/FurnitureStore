package com.example.demo.service;

import com.example.demo.exception.ProductInCartNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
