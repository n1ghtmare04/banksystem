package com.bankmanagement.banksystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankmanagement.banksystem.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
