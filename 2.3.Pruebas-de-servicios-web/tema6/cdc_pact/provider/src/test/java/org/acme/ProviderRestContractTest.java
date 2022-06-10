package org.acme;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;

import com.github.javafaker.Faker;

import org.acme.model.Student;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;


import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Provider("student-provider")
@Consumer("student-consumer-rest")
@PactFolder("../consumer/target/pacts")
@QuarkusTest
public class ProviderRestContractTest {

    private static final String NO_STUDENTS_STATE = "No students exist";
    private static final String STUDENTS_STATE = "Students exist";
    private static final String A_STUDENT_STATE = "Student 1 exists";

    @ConfigProperty(name = "quarkus.http.test-port")
    int quarkusPort;

    Student st1 = createFakeStudent("1");

    Student st2 = createFakeStudent("2");

    @BeforeEach
    public void setUp(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", quarkusPort));

        // Could not use CDI Beans in state callbacks so we can not prepare the data before each Interaction.
        // Could no use mocks requests either. The best solution to manage State with its corresponding response
        // in @BeforeEach anotatted method. It can be done in @AfterEach but has no sense because data should be
        // prepared before Interaction and no after Interaction.


        // Prepare data before next State/Interaction. This solution is a workaround found in
        // https://github.com/quarkusio/quarkus/issues/22611#issuecomment-1270630532 that allows
        // To prepare data before each Interaction. Could be used in @AfterEach or @BeforeEach anotatted methods
        
        boolean noStudents = Optional.ofNullable(context.getInteraction().getProviderStates())
        .orElseGet(List::of)
        .stream()
        .filter(state -> NO_STUDENTS_STATE.equals(state.getName()))
        .count() > 0;

        boolean students = Optional.ofNullable(context.getInteraction().getProviderStates())
        .orElseGet(List::of)
        .stream()
        .filter(state -> STUDENTS_STATE.equals(state.getName()))
        .count() > 0;

        boolean astudent = Optional.ofNullable(context.getInteraction().getProviderStates())
        .orElseGet(List::of)
        .stream()
        .filter(state -> A_STUDENT_STATE.equals(state.getName()))
        .count() > 0;

        if (noStudents) {
            PanacheMock.mock(Student.class);
            given(Student.listAll())
            .willReturn(Collections.emptyList());
        }

        if (students) {
            PanacheMock.mock(Student.class);
            given(Student.listAll())
            .willReturn(List.of(st1,st2));
        }

        if (astudent) {
            PanacheMock.mock(Student.class);
            given(Student.findById("1"))
            .willReturn(st1);
        }
    }


    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State(A_STUDENT_STATE)
    public void student1Exists() {
        // handled in @BeforeEach
    }

    @State(STUDENTS_STATE)
    public void studentsExist() {
        // handled in @BeforeEach
    }

    @State(NO_STUDENTS_STATE)
    public void noStudentExist() {
        // handled in @BeforeEach
    }

    private Student createFakeStudent(String id) {
        Faker faker = new Faker();
        return Student.builder()
                .id(id)
                .name(faker.name().firstName())
                .studentNumber(faker.number().randomNumber())
                .email(faker.internet().emailAddress()).build();
    }

}