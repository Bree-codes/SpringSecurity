package com.bree.springproject.springsecuritydemo.service;

import com.bree.springproject.springsecuritydemo.DTO.SignUpRequest;
import com.bree.springproject.springsecuritydemo.entity.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
}
