package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Role;
import com.teamchallenge.online_store.repository.RoleRepository;

public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

}
