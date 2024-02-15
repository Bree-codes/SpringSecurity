package com.bree.springproject.springsecuritydemo.service;

import com.bree.springproject.springsecuritydemo.DTO.JwtAuthenticationResponse;
import com.bree.springproject.springsecuritydemo.DTO.LoginRequest;
import com.bree.springproject.springsecuritydemo.DTO.RefreshTokenRequest;
import com.bree.springproject.springsecuritydemo.DTO.SignUpRequest;
import com.bree.springproject.springsecuritydemo.entity.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse login(LoginRequest loginRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
