package com.agatap.veshje.repository;

import com.agatap.veshje.model.PaymentsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsTypeRepository extends JpaRepository<PaymentsType, Integer> {
    boolean existsByName(String name);
}
