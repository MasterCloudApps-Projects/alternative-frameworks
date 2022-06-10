package org.acme;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse.Status;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @ReactiveTransactional
    public Uni<User> createUser(User user) {
        return userRepository.persist(user);
    }

    public Uni<List<User>> getUsers(Optional<String> firstName) {
        return firstName
            .map(fn -> userRepository.find("firstName", fn).list())
            .orElseGet(() -> userRepository.listAll());
    }

    public Uni<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @ReactiveTransactional
    public Uni<Response> deleteUser(Long id) {
        return userRepository.deleteById(id)
            .map(deleted -> deleted
            ? Response.ok().status(Status.NO_CONTENT).build()
            : Response.ok().status(Status.NOT_FOUND).build());
        // Can not find and delete to return the deleted entity in the same method
        // Persist and delete requires @ReactiveTransactional or Panache.withTransaction(() -> ...
    }
}
