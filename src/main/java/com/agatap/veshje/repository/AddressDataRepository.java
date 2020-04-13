package com.agatap.veshje.repository;

import com.agatap.veshje.model.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDataRepository extends JpaRepository<AddressData, Integer> {
}
