package com.bree.springproject.springsecuritydemo.service.implementation;

import com.bree.springproject.springsecuritydemo.DTO.SignUpRequest;
import com.bree.springproject.springsecuritydemo.entity.Role;
import com.bree.springproject.springsecuritydemo.entity.User;
import com.bree.springproject.springsecuritydemo.repository.UserRepository;
import com.bree.springproject.springsecuritydemo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   public User signup(SignUpRequest signUpRequest){

       User user = new User();
       user.setEmail(signUpRequest.getEmail());
       user.setFirstname(signUpRequest.getFirstname());
       user.setLastname(signUpRequest.getLastname());
       user.setRole(Role.USER);
       user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

       return userRepository.save(user);

  }
}
