package com.bree.springproject.springjwt.model;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String jwt, String userRegistrationWasSuccessful) {

    }
}
