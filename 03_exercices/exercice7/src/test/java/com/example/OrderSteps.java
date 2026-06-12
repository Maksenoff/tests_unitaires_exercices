package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderSteps {
    private ProductCatalog productCatalog;
    private OrderService orderService;
    private OrderReceipt orderReceipt;
    private Exception exception;

    @Given("a product {string} named {string} costs {bigdecimal} with stock {int}")
    public void aProductNamedCostsWithStock(String reference, String name, BigDecimal unitPrice, int stock) {
        productCatalog = mock(ProductCatalog.class);
        orderService = new OrderService(productCatalog);
        exception = null;
        orderReceipt = null;

        Product product = new Product(reference, name, unitPrice, stock);

        when(productCatalog.findByReference(reference)).thenReturn(Optional.of(product));
    }

    @Given("product {string} does not exist")
    public void productDoesNotExist(String reference) {
        productCatalog = mock(ProductCatalog.class);
        orderService = new OrderService(productCatalog);
        exception = null;
        orderReceipt = null;

        when(productCatalog.findByReference(reference)).thenReturn(Optional.empty());
    }

    @When("customer {string} with status STANDARD orders {int} products {string}")
    public void customerWithStatusStandardOrdersProducts(String email, int quantity, String productReference) {
        placeOrder(email, CustomerStatus.STANDARD, productReference, quantity);
    }

    @When("customer {string} with status PREMIUM orders {int} products {string}")
    public void customerWithStatusPremiumOrdersProducts(String email, int quantity, String productReference) {
        placeOrder(email, CustomerStatus.PREMIUM, productReference, quantity);
    }

    @When("customer {string} with status VIP orders {int} products {string}")
    public void customerWithStatusVipOrdersProducts(String email, int quantity, String productReference) {
        placeOrder(email, CustomerStatus.VIP, productReference, quantity);
    }

    @Then("the order receipt should not be empty")
    public void theOrderReceiptShouldNotBeEmpty() {
        assertNotNull(orderReceipt);
    }

    @Then("the order receipt should be empty")
    public void theOrderReceiptShouldBeEmpty() {
        assertNull(orderReceipt);
    }

    @Then("the order total should be {bigdecimal}")
    public void theOrderTotalShouldBe(BigDecimal expectedTotal) {
        assertEquals(0, expectedTotal.compareTo(orderReceipt.totalAmount()));
    }

    @Then("the confirmation message should be {string}")
    public void theConfirmationMessageShouldBe(String expectedMessage) {
        assertEquals(expectedMessage, orderReceipt.message());
    }

    @Then("an order error should be thrown with message {string}")
    public void anOrderErrorShouldBeThrownWithMessage(String expectedMessage) {
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Then("the product catalog should have searched product {string}")
    public void theProductCatalogShouldHaveSearchedProduct(String reference) {
        verify(productCatalog).findByReference(reference);
    }

    private void placeOrder(String email, CustomerStatus status, String productReference, int quantity) {
        try {
            orderReceipt = orderService.placeOrder(email, status, productReference, quantity);
        } catch (Exception caughtException) {
            exception = caughtException;
        }
    }
}
