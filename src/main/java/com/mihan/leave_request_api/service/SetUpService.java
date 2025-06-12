package com.mihan.leave_request_api.service;

import com.mihan.leave_request_api.model.Permission;
import com.mihan.leave_request_api.model.Role;
import com.mihan.leave_request_api.model.User;
import com.mihan.leave_request_api.repo.PermissonRepo;
import com.mihan.leave_request_api.repo.RoleRepo;
import com.mihan.leave_request_api.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SetUpService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissonRepo permissonRepo;

    @Autowired
    private UserRepo userRepo;

    public void setUp() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        //adding permissions
        List<Permission> permissions = new ArrayList<>();

        permissions.add(new Permission("GET_ALL", "permission to get own all leave data"));
        permissions.add(new Permission("GET_ALL_USERS", "permission to get all user leave data"));
        permissions.add(new Permission("CREATE_LEAVE", "permission to create a leve request"));
        permissions.add(new Permission("UPDATE_LEAVE", "permission to get update leve data"));
        permissions.add(new Permission("DELETE_LEAVE", "permission to delete leve data"));

        permissonRepo.saveAll(permissions);


        //user role and permission setup

        Set<Permission> userPermissions = new HashSet<>();

        Permission getAll = permissonRepo.findByname("GET_ALL").get();
        Permission createLeave = permissonRepo.findByname("CREATE_LEAVE").get();
        Permission updateLeave = permissonRepo.findByname("UPDATE_LEAVE").get();

        userPermissions.add(getAll);
        userPermissions.add(createLeave);
        userPermissions.add(updateLeave);

        Role userRole = new Role();
        userRole.setRolename("USER");
        userRole.setPermissions(userPermissions);
        roleRepo.save(userRole);

        //admin role and permission setup

        Set<Permission> adminPermissions = new HashSet<>();

        Permission getAllUserLeaves = permissonRepo.findByname("GET_ALL_USERS").get();
        Permission deleteLeave = permissonRepo.findByname("DELETE_LEAVE").get();


        adminPermissions.add(getAllUserLeaves);
        adminPermissions.add(createLeave);
        adminPermissions.add(updateLeave);
        adminPermissions.add(deleteLeave);


        Role adminRole = new Role();
        adminRole.setRolename("ADMIN");
        adminRole.setPermissions(adminPermissions);
        roleRepo.save(adminRole);

        //admin creation
        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword(encoder.encode("123"));
        admin.setRole(roleRepo.findByrolename("ADMIN").get());

        userRepo.save(admin);

    }


}
