package com.example.demo.controller;

import com.example.demo.dto.request.CreateProductRequest;
import com.example.demo.dto.response.CartDto;
import com.example.demo.dto.response.ProductDto;
import com.example.demo.dto.response.SuccessResponse;
import com.example.demo.model.*;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest createProductRequest) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<ProductTag> tags = createProductRequest.getTags();

        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .createUser(user)
                .imageUrl("http://localhost:8080/uploads/products/default.png")
                .tags(createProductRequest.getTags())
                .category(ProductCategory.ALL)
                .build();

        for (ProductTag tag : tags) {
            tag.setProduct(product);
        }

        product.setTags(tags);
        productService.create(product);

        ProductDto productDto = productService.toDTO(product);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) throws Exception {

        productService.deleteById(id);

        return ResponseEntity.ok(new SuccessResponse("Продукт удален", HttpStatus.OK));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProductDto>> getAllUserProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<ProductDto> products = productService.getUserAllProducts(user.getId());
        if (products == null) {
            products = new ArrayList<>();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<ProductDto> products = productService.getAllProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all/{category}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(
            @PathVariable ProductCategory category) {

        List<ProductDto> products = productService.getAllProductsByCategory(category);
        if (products == null) {
            products = new ArrayList<>();
        }
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) throws Exception {

        productService.updateById(id, productDto);

        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> updateImageUrl(@PathVariable Long id,
                                             @RequestParam("file") MultipartFile imageUrlRequest)  throws Exception {
        productService.uploadImage(id, imageUrlRequest);

        return ResponseEntity.ok(new SuccessResponse(
                "Изображение обновлено!",
                HttpStatus.OK
        ));
    }

    @PostMapping("/{id}/put-in-cart")
    public ResponseEntity<?> putInCart(@PathVariable Long id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Cart cart = Cart.builder()
                .product(productService.getProductById(id))
                .putByUser(user)
                .build();

        cartService.putProduct(cart);

        return ResponseEntity.ok(new SuccessResponse(
                "Товар добавлен в корзину!",
                HttpStatus.OK
        ));
    }

    @DeleteMapping("/{id}/delete-from-cart")
    public ResponseEntity<?> deleteFromCart(@PathVariable Long id) throws Exception {

        cartService.deleteById(id);

        return ResponseEntity.ok(new SuccessResponse("Продукт удален из корзины!", HttpStatus.OK));
    }

    @GetMapping("/cart/")
    public ResponseEntity<List<CartDto>> getAllProductsFromCart() {
        List<CartDto> products = cartService.getAllProducts();
        if (products == null) {
            products = new ArrayList<>();
        }
        return ResponseEntity.ok(products);
    }
}