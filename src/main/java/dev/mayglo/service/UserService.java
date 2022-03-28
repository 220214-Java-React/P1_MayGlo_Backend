package dev.mayglo.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.mayglo.model.User;
import dev.mayglo.repo.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * UserService carries out tasks that need to be done before calling the repository methods to
 * alter information in the database.
 */
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger;
    private final BCrypt.Hasher hasher;

    /**
     * Initializes the UserService class. Includes a logger, a Bcrypt hasher, and a reference to the UserRepository.
     */
    public UserService() {
        this.logger = LogManager.getLogger(UserRepository.class.getName());
        // Create a new reference to the UserRepository
        this.userRepository = new UserRepository();
        // Initialize BCrypt
        this.hasher = BCrypt.withDefaults();
    }

    /**
     * Creates a user. Encrypts the user's password with Bcrypt. Checks that the username and password are not taken.
     * @param user User to be created
     */
    public void create(User user) {
        String encryptedPass = encryptPassword(user.getPassword());
        user.setPassword(encryptedPass);
        userRepository.create(user);
    }

    /**
     * Gets a User by their ID number.
     * @param i The ID number
     * @return User corresponding with the provided ID number
     */
    public User getByID(Integer i) {
        return userRepository.getByID(i);
    }

    /**
     * Returns a list of all Users in the database.
     * @return A User List
     */
    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    /**
     * Updates a User.
     * @param user User to update
     */
    public void update(User user) {
        userRepository.update(user);
    }

    /**
     * Deletes a User.
     * @param user User to delete
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Encrypts a password using Bcrypt.
     * @param password Password to encrypt
     * @return Encrypted password
     */
    private String encryptPassword(String password){
        return hasher.hashToString(4, password.toCharArray());
    }
}
