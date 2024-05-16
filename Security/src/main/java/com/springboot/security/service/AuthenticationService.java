package com.springboot.security.service;

import com.springboot.security.Dto.AuthorizationResponse;
import com.springboot.security.Dto.RegistrationRequest;
import com.springboot.security.entity.User;
import com.springboot.security.repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    public ResponseEntity<AuthorizationResponse> registerUser(
            RegistrationRequest registrationRequest, HttpServletResponse httpServletResponse){

        User user = new User();

        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setUsername(registrationRequest.getUsername());

    }
}
