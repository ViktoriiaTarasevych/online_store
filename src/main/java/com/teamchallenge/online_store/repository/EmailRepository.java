package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.EmailRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailRequest, Long> {
}
