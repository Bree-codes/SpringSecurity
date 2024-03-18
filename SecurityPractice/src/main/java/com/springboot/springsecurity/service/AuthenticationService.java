package com.springboot.springsecurity.service;

import com.springboot.springsecurity.entity.Token;
import com.springboot.springsecurity.entity.User;
import com.springboot.springsecurity.model.AuthenticationResponse;
import com.springboot.springsecurity.repository.TokenRepository;
import com.springboot.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationService(JwtService jwtService,
                                 UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public AuthenticationResponse register(User userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        user = repository.save(user);

        String token = jwtService.generateToken(user);

        //saving the token
        saveUserToken(token, user);

        return new AuthenticationResponse(token);
    }

    public <List> AuthenticationResponse authenticate(User userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(

                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );

        User user = repository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(token,user);

        return new AuthenticationResponse(token);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokensByUser(user.getId());
        if(!validTokenListByUser.isEmpty()){
            validTokenListByUser.forEach(t->{
                t.setIsLoggedOut(true);
            });
        }

        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(String token, User user) {
        Token token1 = new Token();
        token1.setToken(token);
        token1.setIsLoggedOut(false);
        token1.setUser(user);

        tokenRepository.save(token1);
    }
}
