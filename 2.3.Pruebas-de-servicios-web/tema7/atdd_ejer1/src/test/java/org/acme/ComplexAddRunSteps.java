package org.acme;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComplexAddRunSteps {
 
    private Complex number;
	private Complex result;
	
	private double real;
	private double img;     

    @Given("^\\((\\d+)\\+(\\d+)i\\)$")
    public void givenNumber(int real, int img) {
        number = new Complex(real, img);
    }
     
    @When("^is added to \\((\\d+)\\+(\\d+)i\\)$")
    public void isAddedToNumber(int real, int img) {
        result = number.add(new Complex(real, img));
    }
    
    @Then("^\\((\\d+)\\+(\\d+)i\\) is obtained$")
    public void isObtained(int real, int img) {
        assertEquals(new Complex(real, img), result);
    }
    
    @When("^parts are requested$")
    public void parts_are_requested() throws Throwable {
    	real = number.getRealPart();
    	img = number.getImaginaryPart();
    }

    @Then("^real part is (\\d+)$")
    public void real_part_is(double real) throws Throwable {
        assertEquals(this.real, real);
    }

    @Then("^imaginary part is (\\d+)$")
    public void imaginary_part_is(double img) throws Throwable {
    	assertEquals(this.img, img);
    }
}