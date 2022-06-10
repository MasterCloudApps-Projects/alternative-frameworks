package org.acme;

import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class KarateTest {

    @Karate.Test
    public Karate testSample() {
        return Karate.run("src/test/resources/items.feature");
    }
    
}
