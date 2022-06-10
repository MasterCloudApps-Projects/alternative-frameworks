package org.acme;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.acme.model.Comment;
import org.acme.model.Post;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@QuarkusTest
public class RestTest {

    @ConfigProperty(name = "quarkus.http.port")
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port + 1;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
	@DisplayName("Crear un post y verificar que se crea correctamente")
	public void createPostTest() throws Exception{

        // CREAMOS UN NUEVO POST

		Post post = new Post("Mi titulo", "Mi contenido");
    	
        Post createdPost = 
            given().
                request()
                    .body(objectMapper.writeValueAsString(post))
                    .contentType(ContentType.JSON).
            when().
                post("/api/post").
            then().
                assertThat().
                statusCode(201).
                body("title", equalTo(post.getTitle()))
                .extract().as(Post.class);

        // COMPROBAMOS QUE EL POST SE HA CREADO CORRECTAMENTE

        when().
            get("/api/post/{id}", createdPost.getId()).
        then().
             assertThat().
             statusCode(200).
             body("title", equalTo(post.getTitle()));
        
    }

    @Test
	@DisplayName("Añadir un comentario a un post y verificar que se añade el comentario")
	public void createCommentTest() throws Exception {

		// CREAMOS UN NUEVO POST

		Post post = new Post("Mi titulo", "Mi contenido");
    	
        Post createdPost = 
            given().
                request()
                    .body(objectMapper.writeValueAsString(post))
                    .contentType(ContentType.JSON).
            when().
                post("/api/post").
            then()
                .extract().as(Post.class);
        
        // CREAMOS UN NUEVO COMENTARIO

        Comment comment = new Comment("Juan", "Buen post");

        given().
            request()
                    .body(objectMapper.writeValueAsString(comment))
                    .contentType(ContentType.JSON).
        when().
            post("/api/post/{postId}/comment", createdPost.getId()).
        then().
            assertThat().
            statusCode(201).
            body("author", equalTo(comment.getAuthor())).
            body("message", equalTo(comment.getMessage()));

        
        // COMPROBAMOS QUE EL COMENTARIO EXISTE

        when().
            get("/api/post/{id}", createdPost.getId()).
        then().
             assertThat().
             statusCode(200).
             body("comments[0].author", equalTo(comment.getAuthor())).
             body("comments[0].message", equalTo(comment.getMessage()));
    
    }

    @Test
	@DisplayName("Borrar un comentario de un post y verificar que no aparece el comentario")
	public void deleteCommentTest() throws Exception {

        // CREAMOS UN NUEVO POST

		Post post = new Post("Mi titulo", "Mi contenido");
    	
        Post createdPost = 
            given().
                request()
                    .body(objectMapper.writeValueAsString(post))
                    .contentType(ContentType.JSON).
            when().
                post("/api/post/").
            then()
                .extract().as(Post.class);
        
        // CREAMOS UN NUEVO COMENTARIO

        Comment comment = new Comment("Juan", "Buen post");

        Comment createdComment = given().
            request()
                    .body(objectMapper.writeValueAsString(comment))
                    .contentType(ContentType.JSON).
        when().
            post("/api/post/{postId}/comment", createdPost.getId()).
        then().
            assertThat().
            statusCode(201).
            body("author", equalTo(comment.getAuthor())).
            body("message", equalTo(comment.getMessage())).
            extract().as(Comment.class);

        // BORRAMOS EL COMENTARIO
        
        when().
             delete(
                 "/api/post/{postId}/comment/{commentId}", 
                 createdPost.getId(), createdComment.getId()
             ).
        then().
             assertThat().
             statusCode(204);


        
        // COMPROBAMOS QUE EL COMENTARIO NO EXISTE

        when().
             get("/api/post/{id}", createdPost.getId()).
        then().
             assertThat().
             statusCode(200).
             body("comments", IsEmptyCollection.empty());
    
    }
    
}