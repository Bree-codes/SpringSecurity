package com.bree.springproject.springsecuritydemo.DTO;

import lombok.Data;

public class LoginRequest {

    @Data
    private String email;
    private String password;

}
