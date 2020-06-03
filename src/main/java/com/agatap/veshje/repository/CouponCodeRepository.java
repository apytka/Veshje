package com.agatap.veshje.repository;

import com.agatap.veshje.model.CouponCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponCodeRepository extends JpaRepository<CouponCode, Integer> {

    boolean existsByCode(String code);
    Optional<CouponCode> findByCode(String code);
}
