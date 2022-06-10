package org.acme;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import io.quarkus.test.junit.QuarkusTest;

import org.acme.service.StudentListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import javax.inject.Inject;

@QuarkusTest
@ExtendWith(PactConsumerTestExt.class)
public class StudentListenerTest {

    @Inject
    StudentListener studentListener;

    @Pact(consumer = "student-consumer-message", provider = "student-provider")
    public MessagePact studentCreatedMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("messageUuid");
        body.object("student")
                .stringType("id", "1")
                .stringType("name", "Fake name")
                .stringType("email", "fake.email@sngular.com")
                .numberType("studentNumber", 23)
                .closeObject();

        return builder
                .expectsToReceive("Student created message")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "studentCreatedMessage", providerType = ProviderType.ASYNCH)
    public void verifyCreatePersonPact(List<Message> messages) {
        Assertions.assertDoesNotThrow(() -> studentListener.consumeMessage(messages.get(0).contentsAsString()));
    }

}
