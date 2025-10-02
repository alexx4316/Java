package main.java.com.tienda.service;

import main.java.com.tienda.Interface.RepositoryInterface;
import main.java.com.tienda.Interface.ServiceInterface;
import main.java.com.tienda.model.Product;
import main.java.com.tienda.validation.ValidationException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class InventoryService implements ServiceInterface {

    private final RepositoryInterface<Product> productRepositoryInterface;
    private int createdCount = 0;
    private int deleteCount = 0;
    private int updateCount = 0;

    public InventoryService(RepositoryInterface<Product> productRepositoryInterface) {
        this.productRepositoryInterface = productRepositoryInterface;
    }

    // Adicionar un producto
    @Override
    public void addProduct(String name, BigDecimal price, int stock, String type) throws ValidationException{
        if (name == null || name.isBlank()){
            throw new ValidationException("Product name cannot be empty");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValidationException("Price must be greater than zero");
        }
        if (stock < 0){
            throw new ValidationException("Stock cannot be negative");
        }
        if (type == null || type.isBlank()){
            throw new ValidationException("Type cannot be empty");
        }

        Product p = new Product(name,price,stock,type);
        productRepositoryInterface.create(p);
        createdCount++;
    }

    // Actualizar productos
    @Override
    public void updateProduct(int id, String name, BigDecimal price, int stock, String type) throws SQLException, ValidationException{
        Product product = new Product(name,price,stock,type);
        product.setId(id);
        boolean updated = productRepositoryInterface.update(product);
        if (!updated){
            throw new SQLException("Product with id" + id + "not found");
        }
        updateCount++;
    }

    // Eliminar producto
    @Override
    public void deleteProduct(int id) throws SQLException{
        boolean deleted = productRepositoryInterface.delete(id);
        if (!deleted){
            throw new SQLException("Product with id " + id + " not found or could not be deleted.");
        }
    }

    // Buscar todos los productos
    @Override
    public List<Product> findAll(){
        return productRepositoryInterface.findAll();
    }

    // Buscar producto por id
    public Product findById(int id) throws SQLException{
        Product product = productRepositoryInterface.findById(id);
        if (product == null){
            throw new SQLException("Product whit id " + id + " not found");
        }
        return product;
   }

    @Override
    public int getCreatedCount() {
        return createdCount;
    }

    @Override
    public int getDeletedCount() {
        return deleteCount;
    }

    @Override
    public int getUpdatedCount() {
        return updateCount;
    }
}
