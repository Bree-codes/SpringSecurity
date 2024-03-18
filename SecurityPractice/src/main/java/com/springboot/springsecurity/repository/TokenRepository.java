package com.springboot.springsecurity.repository;

import com.springboot.springsecurity.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {

}
