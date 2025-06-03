package com.bankmanagement.banksystem.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankmanagement.banksystem.entities.User;
import com.bankmanagement.banksystem.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService{
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String userLogin) throws UsernameNotFoundException {
        
        User user = userRepository.findByUserLogin(userLogin).
        orElseThrow(() -> new UsernameNotFoundException("User login was not found"));
        
        Set<GrantedAuthority> authorities = user.getRoles().stream().
        map((role) -> new SimpleGrantedAuthority(role.getName())).
        collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
            userLogin,
            user.getUserPassword(),
            authorities
        );
    }
}
