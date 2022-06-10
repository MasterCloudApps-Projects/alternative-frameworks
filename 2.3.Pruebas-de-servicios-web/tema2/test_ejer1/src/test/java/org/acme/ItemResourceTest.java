package org.acme;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemResourceTest {

    @Test
    @Order(1)
    public void testGetAllItemsTest() {
        
        given()
          .when().get("/items/")
          .then()
          .statusCode(Status.OK.getStatusCode())
          .body("[0].description", is("Leche"))
          .and()
          .body("[0].checked", is(true));
    }


	@Test
	@Order(2)
	public void testOneItemTest() throws Exception {

        given()
        .when().get("/items/1")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body("description", is("Leche"))
        .and()
        .body("checked", is(true));			
	}

	@Test
	@Order(3)
	public void testPostItemTest() throws Exception {

		Item item = new Item();
		String itemDescription = "Pan";
		boolean itemCheked = false;
		item.setDescription(itemDescription);
		item.setChecked(itemCheked);

        given()
        .contentType(ContentType.JSON)
        .body(item)
        .when().post("/items/")
        .then()
        .statusCode(Status.CREATED.getStatusCode())
        .body("description", is(itemDescription))
        .and()
        .body("checked", is(itemCheked));	
        
        given()
        .when().get("/items/2")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body("description", is(itemDescription))
        .and()
        .body("checked", is(itemCheked));
		
	}

	@Test
	@Order(4)
	public void testPutItemTest() throws Exception {

		Item item = new Item();
		String itemDescription = "Leche de soja";
		boolean itemCheked = true;
		item.setDescription(itemDescription);
		item.setChecked(itemCheked);

        given()
        .contentType(ContentType.JSON)
        .body(item)
        .when().put("/items/1")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body("description", is(itemDescription))
        .and()
        .body("checked", is(itemCheked));	

        given()
        .when().get("/items/1")
        .then()
        .statusCode(Status.OK.getStatusCode())
        .body("description", is(itemDescription))
        .and()
        .body("checked", is(itemCheked));
				
	}

	@Test
	@Order(5)
	public void testDeleteItemTest() throws Exception {

        given()
        .when().get("/items/1")
        .then()
        .statusCode(Status.OK.getStatusCode());		
	}
    
}
