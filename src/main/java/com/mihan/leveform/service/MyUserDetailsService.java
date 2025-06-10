package com.mihan.leveform.service;


import com.mihan.leveform.model.User;
import com.mihan.leveform.repo.UserRepo;
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
        User user= userRepo.findByUsername(username).get();

        if(user == null){
            System.out.println("User "+username+"404");
            throw  new UsernameNotFoundException("User "+username+"404");
        }
        return user;
    }
}
