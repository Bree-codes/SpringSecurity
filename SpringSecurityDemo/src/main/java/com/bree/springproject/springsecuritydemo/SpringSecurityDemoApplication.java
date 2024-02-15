package com.bree.springproject.springsecuritydemo;

import com.bree.springproject.springsecuritydemo.entity.Role;
import com.bree.springproject.springsecuritydemo.entity.User;
import com.bree.springproject.springsecuritydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityDemoApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    public void run(String... args){
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if(null == adminAccount){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setFirstname("Stephen");
            user.setLastname("Muiru");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin001"));
            userRepository.save(user);
        }
    }
}
