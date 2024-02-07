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
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigninKey() {
        byte [] key = Decoders.BASE64.decode("f8d17bee3760f108132332e970eb8a0b87f74499197db195996a8ba7b5ddaf08");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token){
       /* return Jwts.ParserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();*/
        return Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

}
