package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DiscountSteps {
    private CustomerRepository customerRepository;
    private DiscountService discountService;
    private int discountRate;

    @Given("an unknown customer with email {string}")
    public void anUnknownCustomerWithEmail(String email) {
        customerRepository = mock(CustomerRepository.class);
        discountService = new DiscountService(customerRepository);

        when(customerRepository.existsByEmail(email)).thenReturn(false);
    }

    @Given("a known customer with email {string} and {int} orders")
    public void aKnownCustomerWithEmailAndOrders(String email, int orderCount) {
        customerRepository = mock(CustomerRepository.class);
        discountService = new DiscountService(customerRepository);

        when(customerRepository.existsByEmail(email)).thenReturn(true);
        when(customerRepository.countOrdersByEmail(email)).thenReturn(orderCount);
    }

    @When("I calculate the discount rate for {string}")
    public void iCalculateTheDiscountRateFor(String email) {
        discountRate = discountService.calculateDiscountRate(email);
    }

    @Then("the discount rate should be {int}")
    public void theDiscountRateShouldBe(int expectedDiscountRate) {
        assertEquals(expectedDiscountRate, discountRate);
    }

    @Then("the customer repository should have checked if {string} exists")
    public void theCustomerRepositoryShouldHaveCheckedIfExists(String email) {
        verify(customerRepository).existsByEmail(email);
    }

    @Then("the customer repository should have counted orders for {string}")
    public void theCustomerRepositoryShouldHaveCountedOrdersFor(String email) {
        verify(customerRepository).countOrdersByEmail(email);
    }
}
