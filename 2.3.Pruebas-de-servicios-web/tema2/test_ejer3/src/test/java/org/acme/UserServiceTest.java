package org.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.acme.manager.UserService;
import org.acme.manager.UsersManagerResource;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(UsersManagerResource.class)
public class UserServiceTest {

	@Inject
	@InjectMock
	@RestClient
	UserService userService;

	private static User user;
	private static User invalidUser;

	private static final String BASE_URL = "http://localhost:8080/users/";

	@BeforeAll
	public static void setUp() {

		user = new User();
		user.setId(1);
		user.setEditable(false);
		user.setEmail("juan@urjc.es");
		user.setName("Juan");

		invalidUser = new User();
		invalidUser.setId(2);
		invalidUser.setEditable(false);
		invalidUser.setEmail("invalidEmail");
		invalidUser.setName("Juan");

	}
	
	@Test
	@Order(1)
	public void getUsersTest() throws URISyntaxException {

		URI location = new URI(BASE_URL + String.valueOf(user.getId()));

		Response userCreatedResponse = Response.created(location).entity(user).build();
		Response userListResponse = Response.ok().entity(List.of(user)).build();

		when(userService.postUser(any(User.class))).thenReturn(userCreatedResponse);
		when(userService.getUsers()).thenReturn(userListResponse);

		given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.when().body(user).post("/")
		.then()		
		.statusCode(Status.CREATED.getStatusCode())
		.extract().as(User.class);

		List<User> response = 
		given()
		.when().get("/")
		.then()
		.statusCode(Status.OK.getStatusCode())
		.extract()
		.body()
		.jsonPath().getList(".", User.class);

		assertEquals(response.size(),1);
	}

	@Test
	@Order(2)
	public void getOneUserTest() {

		Response userResponse = Response.ok().entity(user).build();
		when(userService.getUser(1)).thenReturn(userResponse);

		User response = 
		given()
		.when().pathParam("id", 1).get("/{id}")
		.then()
		.statusCode(Status.OK.getStatusCode())
		.extract()
		.body().as(User.class);

		assertEquals(response.getName(),"Juan");

		
	}

	@Test
	@Order(3)
	public void postInvalidUserTest() {

		given()
		.contentType(ContentType.JSON)
		.when().body(invalidUser).post("/")
		.then()
		.assertThat().statusCode(equalTo(Status.BAD_REQUEST.getStatusCode()));
	}

}