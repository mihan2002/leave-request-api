package com.mihan.leave_request_api.controller;

import com.mihan.leave_request_api.dto.LoginRequestDto;
import com.mihan.leave_request_api.dto.RegisterRequestDto;
import com.mihan.leave_request_api.service.AuthService;
import com.mihan.leave_request_api.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuditService auditService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto dto, HttpServletRequest request) {
        try {
            String token = service.register(dto);
            auditService.log(dto.getUsername(), "REGISTER", "User", request.getRemoteAddr(), "User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            auditService.log(dto.getUsername(), "REGISTER_FAILED", "User", request.getRemoteAddr(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            auditService.log(dto.getUsername(), "REGISTER_FAILED", "User", request.getRemoteAddr(), "Server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Server error during registration"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto, HttpServletRequest request) {
        try {
            String token = service.loginUser(dto);
            //log.info("Received request for /hello");
            auditService.log(dto.getUsername(), "LOGIN", "User", request.getRemoteAddr(), "Login successful");
            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            auditService.log(dto.getUsername(), "LOGIN_FAILED", "User", request.getRemoteAddr(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
        catch (UsernameNotFoundException e){
            auditService.log(dto.getUsername(), "LOGIN_USER_NOT_FOUND", "User", request.getRemoteAddr(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));

        }
        catch (Exception e) {
            auditService.log(dto.getUsername(), "LOGIN_FAILED", "User", request.getRemoteAddr(), "Server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Server error during login"));
        }
    }
}
