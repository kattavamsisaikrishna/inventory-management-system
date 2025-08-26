package com.vamsi.inventory.config;

import com.vamsi.inventory.auth.entity.Role;
import com.vamsi.inventory.auth.entity.User;
import com.vamsi.inventory.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeedConfig {

    @Bean
    public CommandLineRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${admin.email}") String adminEmail,
            @Value("${admin.password}") String adminPassword
    ){
        return args -> {
            if(!userRepository.existsByEmailIgnoreCase(adminEmail)){
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.getRoles().add(Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }
}
