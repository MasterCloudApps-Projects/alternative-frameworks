package org.acme;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.QuarkusMainIntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;


@QuarkusMainIntegrationTest
public class GoogleBooksAPITest {


	@Test
	public void getGoogleBooksTest() {
	
	given().
		param("q", "intitle:javascript").
	 when().
		 get("https://www.googleapis.com/books/v1/volumes").
	 then().
		statusCode(200).
		body("items.volumeInfo.title", hasItems(containsString(("JavaScript"))));
	}

}