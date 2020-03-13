package com.agatap.veshje.repository;

import com.agatap.veshje.model.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimensionRepository extends JpaRepository<Dimension, Integer> {
    boolean existsByBustAndWaistAndHips(Integer bust, Integer waist, Integer hips);
}
