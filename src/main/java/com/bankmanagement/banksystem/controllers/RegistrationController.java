package com.bankmanagement.banksystem.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bankmanagement.banksystem.dto.RegisterUserRequest;
import com.bankmanagement.banksystem.mappers.UserMapper;
import com.bankmanagement.banksystem.repositories.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/sign-up")
public class RegistrationController {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // Registers new users based on variables 
    // String userName, String email, String userLogin,
    // String userPassword, int yearsCountry, int employed, Long totalLoans,
    // Long userBalance, int isAdmin, String contactNumber
    @PostMapping("")
    public ResponseEntity<String> registerUser (
        UriComponentsBuilder uriBuilder,
        @RequestBody RegisterUserRequest request
    ) {
        if (userRepository.findByUserLogin(request.getUserLogin()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this login already exists");
        }

        if (!request.getUserPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must include at least 1 digit, 1 lowercase, 1 uppercase and 1 special character and be 8 characters long");
        }

        if (!request.getContactNumber().matches("^\\d{11}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contact number must consist of digits only and be 11 characters long");
        }

        var user = userMapper.toEntity(request);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("Registration success");
    }
}
