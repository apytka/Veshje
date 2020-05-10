package com.agatap.veshje.repository;

import com.agatap.veshje.model.Product;
import com.agatap.veshje.model.SizeType;
import com.agatap.veshje.model.TypeCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
    List<Product> findByTypeCollection(TypeCollection type);
    List<Product> findByOrderByPriceAsc();
    List<Product> findByOrderByPriceDesc();
    Optional<Product> findById(String id);
}
