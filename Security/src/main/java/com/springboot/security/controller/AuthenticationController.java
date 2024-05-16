package com.springboot.security.controller;

import com.springboot.security.Dto.AuthorizationResponse;
import com.springboot.security.Dto.RegistrationRequest;
import com.springboot.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bree/api/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthorizationResponse> register
            (@RequestBody RegistrationRequest registrationRequest, HttpServletResponse response) {
        return authenticationService.registerUser(registrationRequest,response);
    }
}
