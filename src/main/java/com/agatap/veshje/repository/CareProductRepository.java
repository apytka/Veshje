package com.agatap.veshje.repository;

import com.agatap.veshje.model.CareProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareProductRepository extends JpaRepository<CareProduct, Integer> {
    boolean existsByName(String name);
}
