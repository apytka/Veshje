package com.agatap.veshje.repository;

import com.agatap.veshje.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    boolean existsByName(String name);
    Optional<Delivery> findByName(String name);
}
