package org.example.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Model.Product;
import org.example.Repositoriy.ProductRepository;
import org.example.Service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchByCategorieSteps {

    private Product productSearch;
    private ProductRepository productRepository;
    private ProductService productService;
    private List<Product> productsFind;

    @Given("User want to search a product by category")
    public void userWantToSearchAProductByCategory() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @When("A user send the category for the research")
    public void aUserSendTheCategoryForTheResearch() {
        productSearch = new Product("ch",0,"Food");
    }

    @And("the app search for all the related product by category")
    public void theAppSearchForAllTheRelatedProductByCategory() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cheese",2.49f,"Food"));
        Mockito.when(productRepository.findByCategory(productSearch.getCategorie())).thenReturn(products);
        productsFind =  productService.findByCategory(productSearch.getCategorie());
    }


    @Then("the app return the products find for the category")
    public void theAppReturnTheProductsFindForTheCategory() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Cheese",2.49f,"Food"));
        Assertions.assertIterableEquals(products, productsFind);
    }
}
