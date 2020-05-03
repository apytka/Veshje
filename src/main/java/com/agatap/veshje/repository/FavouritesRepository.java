package com.agatap.veshje.repository;

import com.agatap.veshje.model.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {
    Optional<Favourites> findByUserId(Integer id);

}
