package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;

import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class KarateTest {

    @ConfigProperty(name = "quarkus.http.port")
    int port;

    @BeforeEach
    public void setPort(){
        System.setProperty("demo.server.port", (port + 1) + "");
    }

    @Karate.Test
    public Karate testSample() {
        return Karate.run("src/test/resources/items.feature");
    }
    
}
