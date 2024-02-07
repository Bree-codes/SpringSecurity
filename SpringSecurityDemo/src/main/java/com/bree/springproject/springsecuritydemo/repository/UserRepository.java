package com.bree.springproject.springsecuritydemo.repository;

import com.bree.springproject.springsecuritydemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
