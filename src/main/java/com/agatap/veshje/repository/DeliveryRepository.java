package com.agatap.veshje.repository;

import com.agatap.veshje.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    boolean existsByName(String name);
}
