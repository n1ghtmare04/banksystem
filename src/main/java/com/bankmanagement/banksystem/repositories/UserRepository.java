package com.bankmanagement.banksystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankmanagement.banksystem.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserLogin(String userLogin);
}
