package org.acme;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

@QuarkusTest
public class UsersResourceTest {

    @InjectMock
    UsersService usersService;

    @Test
    @TestHTTPEndpoint(UsersResource.class) 
	  public void testGetAnuncio() {

    List<User> users = Arrays.asList(new User("John"), new User("Peter"));

    when(usersService.getUsers()).thenReturn(users);

    List<User> response = 
      given()
        .when().get()
        .then()
        .statusCode(Status.OK.getStatusCode())
        .extract()
        .body()
        .jsonPath().getList(".", User.class);

    assertEquals(response.size(), 2);
    assertEquals(response.get(0).getName(), "John");

    }

}