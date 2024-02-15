package com.bree.springproject.springsecuritydemo.configuration;

import com.bree.springproject.springsecuritydemo.service.JwtService;
import com.bree.springproject.springsecuritydemo.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if(StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith
                    (authHeader,"Bearer")){
                filterChain.doFilter(request,response);
                return;
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUserName(jwt);

           if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()== null){
               UserDetails userDetails = userService.userDetailsService().loadUserByUserame(userEmail);
           }



    }

}
