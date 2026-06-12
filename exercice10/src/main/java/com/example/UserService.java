package com.example;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ConfirmationAccount register(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Identifiant déjà utilisé");
        }
        userRepository.save(new User(username, email, password));
        return new ConfirmationAccount("Compte créé avec succès");
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Identifiants invalides"));
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Identifiants invalides");
        }
        return user;
    }
}
