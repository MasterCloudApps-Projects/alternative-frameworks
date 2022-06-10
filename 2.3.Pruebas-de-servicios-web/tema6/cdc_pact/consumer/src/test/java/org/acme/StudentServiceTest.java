package org.acme;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.quarkus.test.junit.QuarkusTest;

import org.acme.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = "student-provider",
        hostInterface = "localhost",
        port = "8082")
public class StudentServiceTest {

    private static final String ENDPOINT = "/students";

    @Pact(consumer = "student-consumer-rest", provider = "student-provider")
    public RequestResponsePact getStudent(PactDslWithProvider builder) {
        return builder.given("Student 1 exists")
                .uponReceiving("get student with ID 1")
                .path("/students/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonBody(object -> {
                    object.stringType("id", "1");
                    object.stringType("name", "Fake name");
                    object.stringType("email", "some.email@sngular.com");
                    object.numberType("studentNumber", 23);
                }).build())
                .toPact();
    }

    @Pact(consumer = "student-consumer-rest", provider = "student-provider")
    public RequestResponsePact getStudents(PactDslWithProvider builder) {
        return builder.given("Students exist")
                .uponReceiving("get all students")
                .path("/students")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonArrayMinLike(2, array ->
                        array.object(object -> {
                            object.stringType("id", "2");
                            object.stringType("name", "Another fake name");
                            object.stringType("email", "another.email@sngular.com");
                            object.numberType("studentNumber", 24);
                        })
                ).build())
                .toPact();
    }

    @Pact(consumer = "student-consumer-rest", provider = "student-provider")
    public RequestResponsePact noStudentsExist(PactDslWithProvider builder) {
        return builder.given("No students exist")
                .uponReceiving("get all students when no student exists")
                .path("/students")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body("[]")
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getStudent", providerName = "student-provider")
    void getStudent_whenStudentExist(MockServer mockServer) {
        Student expected = Student.builder()
                .id("1")
                .name("Fake name")
                .email("some.email@sngular.com")
                .studentNumber(23L).build();

        Response response = ClientBuilder.newClient()
            .target(mockServer.getUrl() + ENDPOINT + "/" + 1)
            .request(MediaType.APPLICATION_JSON)
            .get();

        Student student = response.readEntity(Student.class);

        assertEquals(expected, student);
    }

    @Test
    @PactTestFor(pactMethod = "getStudents", providerName = "student-provider")
    void getStudents_whenStudentsExist(MockServer mockServer) {
        Student student = Student.builder()
                .id("2")
                .name("Another fake name")
                .email("another.email@sngular.com")
                .studentNumber(24L).build();

        Response response = ClientBuilder.newClient()
            .target(mockServer.getUrl() + ENDPOINT)
            .request(MediaType.APPLICATION_JSON)
            .get();

        List<Student> students = response.readEntity(new GenericType<List<Student>>(){});
        List<Student> expected = List.of(student, student);

        assertEquals(expected, students);
    }

    @Test
    @PactTestFor(pactMethod = "noStudentsExist", providerName = "student-provider")
    void getStudents_whenNoStudentsExist(MockServer mockServer) {

        Response response = ClientBuilder.newClient()
            .target(mockServer.getUrl() + ENDPOINT)
            .request(MediaType.APPLICATION_JSON)
            .get();

        List<Student> students = response.readEntity(new GenericType<List<Student>>(){});
        
        assertEquals(Collections.emptyList(), students);
    }
}
