package com.mihan.leave_request_api.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rolename;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}


