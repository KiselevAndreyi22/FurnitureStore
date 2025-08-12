package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteById(Long id);

    @Query("""
    SELECT p
    from Product p
    WHERE p.createUser.id = :userId
""")
    List<Product> findByCreateUserId(@Param("userId") long id);
}
