package dev.mayglo.DAO;

import java.util.List;

public interface MainDAO<T>
{
    void create(T t);
    T get(T t);
    List<T> getAll();
    void update(T t);
    void delete(T t);
}
