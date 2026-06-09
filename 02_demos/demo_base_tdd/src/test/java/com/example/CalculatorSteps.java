package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSteps {
    private Calculator calculator;
    private int result;

    @Given("a calculator")
    public void aCalculator() {
        calculator = new Calculator();
    }

    @When("I add {int} and {int}")
    public void iAddAnd(int left, int right) {
        result = calculator.add(left, right);
    }

    @When("I subtract {int} and {int}")
    public void iSubtractAnd(int left, int right) {
        result = calculator.subtract(left, right);
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int expected) {
        assertEquals(expected, result);
    }
}
