package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorSteps {
    private Calculator calculator;
    private int result;
    private boolean booleanResult;
    private int[] rangeResult;
    private Exception exception;

    @Given("a calculator")
    public void aCalculator() {
        calculator = new Calculator();

        assertNotNull(calculator);
    }

    @When("I add {int} and {int}")
    public void iAddAnd(int left, int right) {
        result = calculator.add(left, right);
    }

    @When("I subtract {int} and {int}")
    public void iSubtractAnd(int left, int right) {
        result = calculator.subtract(left, right);
    }

    @When("I multiply {int} and {int}")
    public void iMultiplyAnd(int left, int right) {
        result = calculator.multiply(left, right);
    }

    @When("I divide {int} by {int}")
    public void iDivideBy(int left, int right) {
        if (right == 0) {
            exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> calculator.divide(left, right)
            );
            return;
        }

        result = calculator.divide(left, right);
    }

    @When("I check if {int} is even")
    public void iCheckIfIsEven(int number) {
        booleanResult = calculator.isEven(number);
    }

    @When("I check if {int} is positive")
    public void iCheckIfIsPositive(int number) {
        booleanResult = calculator.isPositive(number);
    }

    @When("I clear the calculator memory")
    public void iClearTheCalculatorMemory() {
        calculator.clearMemory();
    }

    @When("I save {int} in calculator memory")
    public void iSaveInCalculatorMemory(int value) {
        calculator.saveInMemory(value);
    }

    @When("I create a range from {int} to {int}")
    public void iCreateARangeFromTo(int start, int end) {
        rangeResult = calculator.createRange(start, end);
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int expected) {
        assertEquals(expected, result);
    }

    @Then("the boolean result should be true")
    public void theBooleanResultShouldBeTrue() {
        assertTrue(booleanResult);
    }

    @Then("the calculator memory should be empty")
    public void theCalculatorMemoryShouldBeEmpty() {
        assertNull(calculator.getMemory());
    }

    @Then("the calculator memory should contain {int}")
    public void theCalculatorMemoryShouldContain(int expected) {
        assertEquals(expected, calculator.getMemory());
    }

    @Then("the range should contain {int}, {int}, {int}, {int} and {int}")
    public void theRangeShouldContainAnd(int first, int second, int third, int fourth, int fifth) {
        int[] expected = {first, second, third, fourth, fifth};

        assertArrayEquals(expected, rangeResult);
    }

    @Then("an error should be thrown with message {string}")
    public void anErrorShouldBeThrownWithMessage(String expectedMessage) {
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

}
