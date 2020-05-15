package com.agatap.veshje.repository;

import com.agatap.veshje.model.OrderAddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddressDataRepository extends JpaRepository<OrderAddressData, Integer> {
}
