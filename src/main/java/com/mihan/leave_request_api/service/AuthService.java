package com.mihan.leave_request_api.service;

import com.mihan.leave_request_api.dto.LoginRequestDto;
import com.mihan.leave_request_api.dto.RegisterRequestDto;
import com.mihan.leave_request_api.model.User;
import com.mihan.leave_request_api.repo.UserRepo;
import com.mihan.leave_request_api.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

            Optional<User> userOpt = userRepo.findByUsername(loginRequest.getUsername());
            if (userOpt.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );

            if (!authentication.isAuthenticated()) {
                throw new BadCredentialsException("Invalid username or password.");
            }

            return jwtUtil.generateToken(loginRequest.getUsername());

        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException("User not found");
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid username or password.");
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during login: " + e.getMessage());
        }
    }
}
