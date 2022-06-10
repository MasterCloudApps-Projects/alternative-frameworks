package org.acme;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(features = "src/test/resources/", glue = "org.acme")
public class CucumberTest extends CucumberQuarkusTest {
    
}
