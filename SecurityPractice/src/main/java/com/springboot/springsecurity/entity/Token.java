package com.springboot.springsecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "token_details")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;
    private Boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
