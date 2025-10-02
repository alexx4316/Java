package main.java.com.tienda.Interface;


import java.util.List;

public interface RepositoryInterface<T> {
    void create(T t);
    T findById(int id);
    List<T> findAll();
    boolean update(T t);
    boolean delete(int id);
}
