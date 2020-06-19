package com.agatap.veshje.repository;

import com.agatap.veshje.model.ContactTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactTopicRepository extends JpaRepository<ContactTopic, Integer> {
}
