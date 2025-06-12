package com.mihan.leave_request_api.repo;

import com.mihan.leave_request_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {

    Optional<Role> findByrolename (String rolename);
}
