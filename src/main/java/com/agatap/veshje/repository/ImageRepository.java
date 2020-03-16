package com.agatap.veshje.repository;

import com.agatap.veshje.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    boolean existsByName(String name);
}
