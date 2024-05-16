package com.springboot.security.service;

import com.springboot.security.Dto.AuthorizationResponse;
import com.springboot.security.Dto.RegistrationRequest;
import com.springboot.security.entity.User;
import com.springboot.security.repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthorizationResponse> registerUser(
            RegistrationRequest registrationRequest, HttpServletResponse httpServletResponse){

        User user = new User();

        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        userRepo.save(user);

        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setMessage("User registered successfully");
        authorizationResponse.setId(user.getId());
        authorizationResponse.setHttpStatus(HttpStatus.CREATED);

        return new ResponseEntity<>(authorizationResponse, HttpStatus.CREATED);
    }
}
