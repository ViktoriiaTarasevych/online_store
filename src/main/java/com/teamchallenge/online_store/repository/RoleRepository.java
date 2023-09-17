package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

