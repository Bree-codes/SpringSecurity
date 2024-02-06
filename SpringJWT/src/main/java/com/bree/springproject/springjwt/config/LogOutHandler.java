package com.bree.springproject.springjwt.config;

import ch.qos.logback.core.subst.Token;
import com.bree.springproject.springjwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class LogOutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    private final Token token;

    public LogOutHandler(TokenRepository tokenRepository, Token token) {
        this.tokenRepository = tokenRepository;
        this.token = token;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if(storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }

    }
}
