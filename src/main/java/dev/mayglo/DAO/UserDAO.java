package dev.mayglo.DAO;

import java.util.List;

public interface UserDAO<T> {

    // Create a User
    void create(T t);

    // Read/Get (self-get, for use by current user?)
    T get(T t);
    
    // Read/Get by ID
    T getByID(Integer i);

    // Get a List of all users
    List<T> getAll();

    // Update a User (for use by current user?)
    void update(T t);

    // Update a User by ID (for use by manager and admins?)
    void updateByID(Integer i);

    // Delete a User
    void delete(T t);


}
