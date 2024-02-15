package com.bree.springproject.springsecuritydemo.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JwtServiceImpl {

    public String generateToken(UserDetails userDetails){
        return builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000 *60 *24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigninKey() {
        byte [] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());

    }
}
