package com.agatap.veshje.repository;

import com.agatap.veshje.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    boolean existsByName(String name);
}
