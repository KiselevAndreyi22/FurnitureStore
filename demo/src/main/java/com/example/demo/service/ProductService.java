package com.example.demo.service;
import com.example.demo.dto.response.ProductDto;
import com.example.demo.dto.response.UserDto;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.UknownFileFormatException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductCategory;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocalStorageService localStorageService;

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

    public List<ProductDto> getAllProductsByCategory(ProductCategory category) {
        List<Product> products = productRepository.findByProductCategory(category);
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
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
        product.setCategory(productDto.getCategory());
        return productRepository.save(product);
    }

    public void uploadImage(Long productId, MultipartFile file) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow();

        String originalFilename = file.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new UknownFileFormatException();
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new Exception("Можно загружать только изображения!");
        }

        String filename = "image_product_" + product.getId() + "_" + System.currentTimeMillis() + extension;
        String fileUrl = localStorageService.uploadFile(file, filename);

        product.setImageUrl("http://localhost:8080/uploads/products/" + filename);
        productRepository.save(product);
    }

}
