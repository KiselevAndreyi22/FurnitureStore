package com.example.demo.service;

import com.example.demo.dto.response.ProductDto;
import com.example.demo.dto.response.UserDto;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
        productRepository.deleteById(id);
    }

    public List<ProductDto> getUserAllProducts(long userId) {
        List<Product> products = productRepository.findByCreateUserId(userId);
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDto toDTO(Product product) {
        UserDto userDto = new UserDto(product.getCreateUser());
        return new ProductDto(product);
    }

    public Product updateById(Long id, ProductDto productDto) {
        if(!productRepository.findById(id).isPresent()) {
            throw new ProductNotFoundException();
        }
        Product product = productRepository.findById(id).get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    public String saveProductImage(Long productId, MultipartFile file) throws Exception {
        String folder = "demo/images/";
        String fileName = file.getOriginalFilename();
        Path folderPath = Paths.get(folder);
        Path filePath = folderPath.resolve(fileName);
        Files.write(filePath, file.getBytes());
        Product product = productRepository.findById(productId).orElseThrow();
        product.setImageUrl("/images/" + fileName);
        productRepository.save(product);

        return product.getImageUrl();
    }
}
