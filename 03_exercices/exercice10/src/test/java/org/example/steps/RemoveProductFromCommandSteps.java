package org.example.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Exception.NotFoundException;
import org.example.Model.Command;
import org.example.Model.Product;
import org.example.Repositoriy.CommandRepository;
import org.example.Service.CommandService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class RemoveProductFromCommandSteps {

    private CommandRepository commandRepository;

    private CommandService commandService;
    private Command command;
    private Product product;

    @Given("User want remove Product from a Command")
    public void userWantRemoveProductFromACommand() {
        commandRepository = Mockito.mock(CommandRepository.class);
        commandService = new CommandService(commandRepository);
        command = new Command(124578);
        product = new Product("Cheese",12.5f,"Food");
        command.addProductQuantity(product,1);
    }

    @Given("User want reduce the quantity of a Product from a Command")
    public void userWantReduceTheQuantityOfAProductFromACommand() {
        commandRepository = Mockito.mock(CommandRepository.class);
        commandService = new CommandService(commandRepository);
        command = new Command(124578);
        product = new Product("Cheese",12.5f,"Food");
        command.addProductQuantity(product,2);
    }

    @When("A app remove product to a command")
    public void aAppRemoveProductToACommand() {
        Mockito.when(commandRepository.findById(command.getId_command())).thenReturn(command);
        commandService.removeProductToCommand(product,command.getId_command());
    }

    @And("the app save the modify command")
    public void theAppSaveTheModifyCommand() {
        Mockito.when((commandRepository.update(command))).thenReturn(command);
        commandService.updateCommand(command);
        Mockito.verify(commandRepository).update(command);
    }

    @Then("the app return modify command")
    public void theAppReturnModifyCommand() {
        Command command1 = new Command(124578);
        Assertions.assertEquals(command1,command);
    }

    @When("A app reduce the quantity of the product to a command")
    public void aAppReduceTheQuantityOfTheProductToACommand() {
        Mockito.when(commandRepository.findById(command.getId_command())).thenReturn(command);
        commandService.removeProductToCommand(product,command.getId_command());
    }

    @Then("the app return modify command with less of the product")
    public void theAppReturnModifyCommandWithLessOfTheProduct() {
        Command command1 = new Command(124578);
        command1.addProductQuantity(product,1);
        Assertions.assertEquals(command1,command);
    }


    @Then("the app return a error NotFoundException")
    public void theAppReturnAErrorNotFoundException() {
        Assertions.assertThrows(NotFoundException.class,()->{commandService.removeProductToCommand(product,command.getId_command());});
    }
}
