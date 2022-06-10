package org.acme;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@QuarkusTest
public class RestAPITest {

	@Test
	public void createNewItemTest() {
		
		given().
			request()
				.body("{ \"description\" : \"Leche\", \"checked\": false }")
				.contentType(ContentType.JSON).
		when().
			post("/items/").
		then().
			statusCode(201).
			body("id", equalTo(1));
		
	}

}