package com.mihan.leveform.service;

import com.mihan.leveform.dto.LoginRequestDto;
import com.mihan.leveform.dto.RegisterRequestDto;
import com.mihan.leveform.model.User;
import com.mihan.leveform.repo.UserRepo;
import com.mihan.leveform.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String register(RegisterRequestDto registerRequestDto) {

        if (userRepo.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User newUser = new User();
        newUser.setUsername(registerRequestDto.getUsername());
        newUser.setPassword(encoder.encode(registerRequestDto.getPassword()));

        userRepo.save(newUser);

        return jwtUtil.generateToken(registerRequestDto.getUsername());
    }

    public String loginUser(LoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );

            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Invalid username or password.");
            }

            return jwtUtil.generateToken(loginRequest.getUsername());

        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid username or password.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during login: " + e.getMessage());
        }
    }
}
