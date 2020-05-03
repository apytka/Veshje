package com.agatap.veshje.repository;

import com.agatap.veshje.model.Care;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareProductRepository extends JpaRepository<Care, Integer> {
    boolean existsByName(String name);
}
