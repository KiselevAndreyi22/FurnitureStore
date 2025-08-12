package com.example.demo.controller;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.ProductDto;
import com.example.demo.dto.response.SuccessResponse;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest createProductRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .createUser(user)
                .build();

        productService.create(product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        productService.deleteById(id);

        return ResponseEntity.ok(new SuccessResponse("Проект удален", HttpStatus.OK));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Product>> getAllUserProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Product> products = productService.getUserAllProducts(user.getId());
        if (products == null) {
            products = new ArrayList<>();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all")
    public ResponseEntity<List <ProductDto>> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<ProductDto> products = productService.getAllProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        return ResponseEntity.ok(products);
    }
}
