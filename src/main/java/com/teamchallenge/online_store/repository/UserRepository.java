package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

