package com.agatap.veshje.repository;

import com.agatap.veshje.model.Token;
import com.agatap.veshje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByToken(String value);
    boolean existsByToken(String value);
    boolean existsByUserId(Integer id);
}
