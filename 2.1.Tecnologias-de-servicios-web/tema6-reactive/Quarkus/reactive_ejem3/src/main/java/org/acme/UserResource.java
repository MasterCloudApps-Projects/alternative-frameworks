package org.acme;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestResponse.Status;


@Path("/users")
public class UserResource {

    UserService userService;

    UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Uni<UserDTO> createUser(UserDTO user) {
        return Uni.createFrom().item(user)
            .map(this::toUser)
            .flatMap(userService::createUser)
            .map(this::toUserDTO);
    }

    @GET
    public Uni<List<UserDTO>> getUsers(@QueryParam("firstName") Optional<String> firstName) throws InterruptedException, ExecutionException {
        return userService.getUsers(firstName).map(this::toUserDTOList);
    }

    @GET
    @Path("/{id}")
    public Uni<Object> getUser(@PathParam("id") Long id) {
        return userService.getUser(id).onItemOrFailure().transformToUni((user, throwable) -> {
            if (user != null) {
                return Uni.createFrom().item(toUserDTO(user));
            } 
            return Uni.createFrom().item(Response.status(Status.NOT_FOUND).entity("User with id "+id+" not found"));
        });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteUser(@PathParam("id") Long id) {
        return userService.deleteUser(id);
    }

    private User toUser(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName());
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName());
    }

    private List<UserDTO> toUserDTOList(List<User> users) {
        return users.parallelStream().map(this::toUserDTO).toList();
    }

}
