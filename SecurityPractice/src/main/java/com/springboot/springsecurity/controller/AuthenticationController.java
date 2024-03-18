package com.springboot.springsecurity.controller;

import com.springboot.springsecurity.entity.User;
import com.springboot.springsecurity.model.AuthenticationResponse;
import com.springboot.springsecurity.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody User userRequest){
        return ResponseEntity.ok(authenticationService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User userRequest){
        return ResponseEntity.ok(authenticationService.authenticate(userRequest));
    }

}
