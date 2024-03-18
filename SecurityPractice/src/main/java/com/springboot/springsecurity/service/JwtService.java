package com.springboot.springsecurity.service;

import com.springboot.springsecurity.entity.User;
import com.springboot.springsecurity.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "d905dac76e5b8cf8c7bb2ad5039dddc0701e0a98abcf4806f99221aff7c9362d";

    private final TokenRepository tokenRepository;

    @Autowired
    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }


    //This function is responsible for extracting a specific claim from the "Claims" object.
    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Boolean isValid(String token, UserDetails userDetails){
        String username = (extractUsername(token));

        boolean isTokenValid = tokenRepository.findByToken(token).
                map(t -> !t.getIsLoggedOut()).orElse(false);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) && isTokenValid;
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
