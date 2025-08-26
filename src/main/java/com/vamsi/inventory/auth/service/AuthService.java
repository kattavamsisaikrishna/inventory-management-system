package com.vamsi.inventory.auth.service;

import com.vamsi.inventory.auth.repository.UserRepository;
import com.vamsi.inventory.auth.dto.AuthResponse;
import com.vamsi.inventory.auth.dto.LoginRequest;
import com.vamsi.inventory.auth.dto.RegisterRequest;
import com.vamsi.inventory.auth.entity.Role;
import com.vamsi.inventory.auth.entity.User;
import com.vamsi.inventory.auth.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public User registerCustomer(RegisterRequest req){
        String email  = req.getEmail().trim().toLowerCase();
        if(userRepository.existsByEmailIgnoreCase(email)){
            throw new IllegalArgumentException("Email already in use : "+email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.getRoles().add(Role.CUSTOMER);
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest req){
        User user = userRepository.findByEmailIgnoreCase(req.getEmail().trim().toLowerCase())
                .orElseThrow(()->new IllegalArgumentException("Invalid Credentials"));
        if(!passwordEncoder.matches(req.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("Invalid Credentials");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",user.getRoles());
        String token = jwtUtil.generateToken(user.getEmail(),claims);
        long expirsIn = Long.parseLong(System.getProperty("jwt.expiration","900000"));
        return new AuthResponse(token,expirsIn/1000L);
    }

}
