package org.acme.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.entity.Role;
import org.acme.entity.User;
import org.acme.model.RoleDTO;
import org.acme.model.UserDTO;
import org.acme.model.UserResponseDTO;
import org.acme.repository.RoleRepository;
import org.acme.repository.UserRepository;
import org.acme.request.LoginRequest;
import org.acme.request.RoleToUserForm;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    @Transactional
	public void init(@Observes StartupEvent ev) {
        this.saveUser(new UserDTO("Nico", "Nico", "password"));
        this.saveUser(new UserDTO("Mica", "Mica", "password"));
        this.saveUser(new UserDTO("Patxi", "Patxi", "password"));
        this.saveUser(new UserDTO("Michel", "Michel", "password"));

        this.saveRole(new RoleDTO("ROLE_USER"));
        this.saveRole(new RoleDTO("ROLE_ADMIN"));

        this.addRoleToUser(new RoleToUserForm("Nico", "ROLE_USER"));
        this.addRoleToUser(new RoleToUserForm("Mica", "ROLE_USER"));
        this.addRoleToUser(new RoleToUserForm("Patxi", "ROLE_USER"));
        this.addRoleToUser(new RoleToUserForm("Michel", "ROLE_USER"));
        this.addRoleToUser(new RoleToUserForm("Mica", "ROLE_ADMIN"));
        this.addRoleToUser(new RoleToUserForm("Patxi", "ROLE_ADMIN"));
	}

    public boolean login (LoginRequest loginRequest) {
        Optional<User> user = userRepository.find("username", loginRequest.getUsername()).firstResultOptional();
        if (user.isPresent()) {
            User u = user.get();
            return BcryptUtil.matches(loginRequest.getPassword(), u.getPassword());
        }
        return false;
    }
    
    public List<UserResponseDTO> getUsers() {
        return userRepository.listAll().stream().map(e -> new UserResponseDTO(
            e.getId(), e.getName(), e.getUsername(), e.getRoles())
        ).collect(Collectors.toList());
    }

    public User getUser(String usernmae) {
        return userRepository.find("username", usernmae).firstResult();
    }

    @Transactional
    public UserResponseDTO saveUser(UserDTO userDTO){
        User user = new User(null, userDTO.getName(), userDTO.getUsername(), userDTO.getPassword(), new HashSet<>());
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        userRepository.persist(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getName(), user.getUsername(), user.getRoles());
        return userResponseDTO;
    }

    @Transactional
    public Role saveRole(RoleDTO roleDTO){
        Role role = new Role(roleDTO.getName());
        roleRepository.persist(role);
        return role;
    }

    @Transactional
    public Response addRoleToUser(RoleToUserForm roleToUserForm) {
        Optional<User> user = userRepository.find("username", roleToUserForm.getUsername()).firstResultOptional();
        Optional<Role> role = roleRepository.find("name", roleToUserForm.getRolename()).firstResultOptional();
        if (user.isPresent()) {
            if (role.isPresent()) {
                User u = user.get();
                u.getRoles().add(role.get());
                userRepository.persist(u);
                UserResponseDTO userResponseDTO = new UserResponseDTO(u.getId(), u.getName(), u.getUsername(), u.getRoles());
                return Response.ok().entity(userResponseDTO).build();
            }
            return Response.status(Status.NOT_FOUND).entity("Role not found").build();
        }
        return Response.status(Status.NOT_FOUND).entity("User not found").build();
    
    }
    
}
