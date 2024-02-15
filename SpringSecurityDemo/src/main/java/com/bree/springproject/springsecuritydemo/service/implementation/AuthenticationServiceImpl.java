package com.bree.springproject.springsecuritydemo.service.implementation;

import com.bree.springproject.springsecuritydemo.DTO.JwtAuthenticationResponse;
import com.bree.springproject.springsecuritydemo.DTO.LoginRequest;
import com.bree.springproject.springsecuritydemo.DTO.SignUpRequest;
import com.bree.springproject.springsecuritydemo.entity.Role;
import com.bree.springproject.springsecuritydemo.entity.User;
import com.bree.springproject.springsecuritydemo.repository.UserRepository;
import com.bree.springproject.springsecuritydemo.service.AuthenticationService;
import com.bree.springproject.springsecuritydemo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authenticationManager;
   private final JwtService jwtService;


   public User signup(SignUpRequest signUpRequest){

       User user = new User();
       user.setEmail(signUpRequest.getEmail());
       user.setFirstname(signUpRequest.getFirstname());
       user.setLastname(signUpRequest.getLastname());
       user.setRole(Role.USER);
       user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

       return userRepository.save(user);
  }

  public JwtAuthenticationResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),loginRequest.getPassword()));
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
      jwtAuthenticationResponse.setRefreshToken(refreshToken);
      return jwtAuthenticationResponse;
  }
}
