package org.acme.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    
}