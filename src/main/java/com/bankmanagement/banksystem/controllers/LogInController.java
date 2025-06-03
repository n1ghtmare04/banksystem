package com.bankmanagement.banksystem.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankmanagement.banksystem.dto.LoginUser;
import com.bankmanagement.banksystem.entities.User;
import com.bankmanagement.banksystem.jwt.JwtAuthResponse;
import com.bankmanagement.banksystem.jwt.JwtTokenProvider;
import com.bankmanagement.banksystem.repositories.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/log-in")
public class LogInController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // Allows users to authorise by typing their login and password; 
    // Requires cookies to temporarily save user Id for further actions
    @PostMapping("")
    public ResponseEntity<JwtAuthResponse> logIn ( 
        @RequestBody LoginUser loginUser,
        HttpServletResponse servletResponse
    ) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUserLogin(),
                        loginUser.getUserPassword()
                );

        Authentication auth;
        try {
            auth = authenticationManager.authenticate(authToken);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtTokenProvider.generateToken(auth);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(jwt, "Bearer");

        Optional<User> user = userRepository.findByUserLogin(loginUser.getUserLogin());

        if (user.isPresent()) {
            Cookie userIdCookie = new Cookie("userId", user.get().getUserId().toString());
            
            userIdCookie.setHttpOnly(true);
            userIdCookie.setSecure(true);
            userIdCookie.setMaxAge(86400);
            userIdCookie.setPath("/");

            servletResponse.addCookie(userIdCookie);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(jwtAuthResponse);
    }
}
