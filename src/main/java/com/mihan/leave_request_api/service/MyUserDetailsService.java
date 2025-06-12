package com.mihan.leave_request_api.service;


import com.mihan.leave_request_api.model.User;
import com.mihan.leave_request_api.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepo.findByUsername(username).get();
            if (user == null) {
                throw new UsernameNotFoundException("User does not exist");
            }
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User does not exist");
        }

    }
}
