package com.bree.springproject.springsecuritydemo.DTO;

import com.bree.springproject.springsecuritydemo.entity.Role;
import lombok.Data;

@Data
public class SignUpRequest  {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;


}
