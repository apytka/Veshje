package com.agatap.veshje.repository;

import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.SizeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
    List<Product> findByOrderByPriceAsc();
    List<Product> findByOrderByPriceDesc();
    Optional<Product> findById(String id);


    @Query("SELECT p FROM Product p where p.name LIKE %?1% " +
            "OR p.description LIKE %?1% " +
            "OR p.color LIKE %?1%")
    List<Product> findAll(String keyword);
}
