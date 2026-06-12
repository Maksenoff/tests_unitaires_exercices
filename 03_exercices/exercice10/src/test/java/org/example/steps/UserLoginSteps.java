package org.example.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Exception.NotFoundException;
import org.example.Exception.WrongPasswordException;
import org.example.Repositoriy.UserRepository;
import org.example.Service.UserService;
import org.example.Model.User;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class UserLoginSteps {

    private UserRepository userRepository;
    private UserService userService ;

    private String message;

    private User user;

    @Given("A User try to connect")
    public void aUserTryToConnect() {
        user = new User();
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @When("A user send is {string} and {string}")
    public void aUserSendIsAnd(String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
    }

    @And("the user is log to the app")
    public void theUserIsLogToTheApp() {
        List<User> usersList = new ArrayList<>();
        usersList.add(new User("root@root.com","root","Root"));
        Mockito.when(userRepository.findByName(user.getUsername())).thenReturn(usersList);
        message = userService.connection(user);
    }

    @Then("there is a message who validate Connection")
    public void thereIsAMessageWhoValidateConnection() {
        Assertions.assertEquals(message,"user is Log:"+user);
    }

    @Then("the user does not exist there is a error")
    public void theUserDoesNotExistThereIsAError() {
        Mockito.when(userRepository.findByName(user.getUsername())).thenReturn(new ArrayList<>());
        Assertions.assertThrows(NotFoundException.class, ()->{userService.connection(user);});
    }

    @Then("wrong password for the user there is a error")
    public void wrongPasswordForTheUserThereIsAError() {
        List<User> usersList = new ArrayList<>();
        usersList.add(new User("root@root.com","root","Pa$$word"));
        Mockito.when(userRepository.findByName(user.getUsername())).thenReturn(usersList);
        Assertions.assertThrows(WrongPasswordException.class,()->{userService.connection(user);});
    }
}
