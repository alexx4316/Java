package main.java.com.tienda.Interface;

import main.java.com.tienda.model.Product;
import main.java.com.tienda.validation.ValidationException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ServiceInterface {
    void addProduct(String name, BigDecimal price, int stock, String type) throws SQLException, ValidationException;
    void updateProduct(int id, String name, BigDecimal price, int stock, String type) throws SQLException, ValidationException;
    void deleteProduct(int id) throws SQLException;
    Product findById(int id) throws SQLException;
    List<Product> findAll() throws SQLException;

    int getCreatedCount();
    int getDeletedCount();
    int getUpdatedCount();
}
