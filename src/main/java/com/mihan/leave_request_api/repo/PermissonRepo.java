package com.mihan.leave_request_api.repo;

import com.mihan.leave_request_api.model.Permission;
import com.mihan.leave_request_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissonRepo extends JpaRepository<Permission,Long> {

    Optional<Permission> findByname (String rolename);
}
