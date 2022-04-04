package dev.mayglo.DAO;

import java.util.List;

public interface MainDAO<T>
{
    /**
     * Method for creating an object to persist into database
     * @param t Object to persist
     */
    void create(T t);

    /**
     * Method for retrieving an object based on its ID from database
     * @param i ID to search by
     * @return Object found
     */
    T getByID(Integer i);

    /**
     * Method for retrieving all specified objects from a database
     * @return The list of objects found
     */
    List<T> getAll();

    /**
     * Method for updating an object in the database
     * @param t Object to update
     */
    void update(T t);

    /**
     * Method for deleting an object from the database
     * @param t Object to delete
     */
    void delete(T t);
}
