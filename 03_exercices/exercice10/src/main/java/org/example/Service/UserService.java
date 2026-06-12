package org.example.Service;

import org.example.Exception.AlreadyExisteException;
import org.example.Exception.NotFoundException;
import org.example.Exception.WrongPasswordException;
import org.example.Repositoriy.UserRepository;
import org.example.Model.User;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(User user) {
        List<User> usersFound = userRepository.findByName(user.getUsername());
        if(usersFound.isEmpty()){
            userRepository.add(user);
            return "user Register! \n"+user;
        }
        throw new AlreadyExisteException("User Already Exist");
    }

    public String connection(User user) {
        List<User> usersFound = userRepository.findByName(user.getUsername());
        if(!usersFound.isEmpty() && !usersFound.stream().filter(u -> u.getUsername().equals(user.getUsername())).toList().isEmpty()){
            if(usersFound.getFirst().getPassword().equals(user.getPassword())){
                return "user is Log:"+user;
            }
            throw new WrongPasswordException();
        }
        throw new NotFoundException();
    }
}
