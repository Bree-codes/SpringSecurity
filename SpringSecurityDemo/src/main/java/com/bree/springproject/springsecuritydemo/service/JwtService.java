package com.bree.springproject.springsecuritydemo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
}
