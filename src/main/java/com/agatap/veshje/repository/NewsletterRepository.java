package com.agatap.veshje.repository;

import com.agatap.veshje.controller.DTO.NewsletterDTO;
import com.agatap.veshje.model.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
    boolean existsByEmail(String email);
    Optional<Newsletter> findByEmail(String email);
}
