package com.agatap.veshje.repository;

import com.agatap.veshje.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByName(String name);
}
