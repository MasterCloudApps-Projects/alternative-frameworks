package org.acme;

import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AnuncioResourceTest {

    @Test
    public void testGetAnuncio() {
        
        given()
          .when().get("/anuncio")
          .then()
          .statusCode(Status.OK.getStatusCode())
          .body("nombre", is("Pepe"));		
    }
    
}
