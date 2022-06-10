package org.acme.model;

import lombok.Data;

import java.util.Set;

import org.acme.entity.Role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String name;

    private String username;

    private Set<Role> roles;
   
}