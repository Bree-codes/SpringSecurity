package com.springboot.security.Dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AuthorizationResponse {

    private String message;
    private HttpStatus httpStatus;
    private Integer id;

}
