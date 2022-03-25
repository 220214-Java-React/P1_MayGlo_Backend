package dev.mayglo.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.mayglo.model.User;
import dev.mayglo.repo.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final Logger logger;
    private final BCrypt.Hasher hasher;


    public UserService() {
        this.logger = LogManager.getLogger(UserRepository.class.getName());
        // Create a new reference to the UserRepository
        this.userRepository = new UserRepository();
        // Initialize BCrypt
        this.hasher = BCrypt.withDefaults();
    }


    public void create(User user) {
        String encryptedPass = encryptPassword(user.getPassword());
        user.setPassword(encryptedPass);
        userRepository.create(user);
    }

    // For JUNIT test
    public User getByID(Integer i)
    {
        return userRepository.getByID(i);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    private String encryptPassword(String password){
        return hasher.hashToString(4, password.toCharArray());
    }
}
