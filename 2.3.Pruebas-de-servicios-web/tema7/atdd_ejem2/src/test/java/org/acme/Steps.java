package org.acme;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Steps {

  Calculator c;

  int result;

  @Given("^I have a calculator$")
  public void i_have_a_calculator() {
    c = new Calculator();
  }

  @When("^I add (-?\\d+) and (-?\\d+)$")
  public void I_add_and(int num1, int num2) {
    result = c.add(num1, num2);
  }


  @Then("^the result should be (-?\\d+)$")
  public void response_is_ok(int num) {
    assertEquals(result, num);
  }

}