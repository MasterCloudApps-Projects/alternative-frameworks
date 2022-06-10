package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.entity.Role;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {
    
}