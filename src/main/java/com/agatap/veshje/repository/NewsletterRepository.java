package com.agatap.veshje.repository;

import com.agatap.veshje.model.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, Integer> {
    boolean existsByEmail(String email);
}
