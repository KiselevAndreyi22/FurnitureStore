package com.example.demo.service;

import com.example.demo.dto.response.ProductDto;
import com.example.demo.dto.response.UserDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getUserAllProducts(long userId) {
        return productRepository.findByCreateUserId(userId);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProductDto toDTO(Product product) {
        UserDto userDto = new UserDto(product.getCreateUser());
        return new ProductDto(product);
    }


}
