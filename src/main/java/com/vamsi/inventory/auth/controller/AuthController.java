package com.vamsi.inventory.auth.controller;

import com.vamsi.inventory.auth.dto.AuthResponse;
import com.vamsi.inventory.auth.dto.LoginRequest;
import com.vamsi.inventory.auth.dto.RegisterRequest;
import com.vamsi.inventory.auth.entity.User;
import com.vamsi.inventory.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        User user =  authService.registerCustomer(request);
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("id",user.getId());
        map.put("email",user.getEmail());
        map.put("role",user.getRoles());
        return ResponseEntity.status(HttpStatus.CREATED).body(map);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/info")
    public ResponseEntity<String> info(){
        return ResponseEntity.ok(authService.info());
    }
}
