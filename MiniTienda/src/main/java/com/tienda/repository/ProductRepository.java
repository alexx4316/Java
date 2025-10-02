package main.java.com.tienda.repository;

import main.java.com.tienda.Interface.RepositoryInterface;
import main.java.com.tienda.model.Product;
import main.java.com.tienda.util.ConnectionDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements RepositoryInterface<Product> {

    private static final String TABLE = "product";

    // Creamos lo metodos del CRUD

    //Crear o guardar
    @Override
    public void create(Product product){
        String sql = "INSERT INTO " + TABLE + " (name, price, stock, type) VALUES (?,?,?,?)";
        try(Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getType());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Error saving product", e);
        }
    }

    // Encontrar un articulo por su codigo
    @Override
    public Product findById(int id){
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        try(Connection conn = ConnectionDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return maprowToProduct(rs);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding Product by id", e);
        }
        return null;
    }

    private Product maprowToProduct(ResultSet rs) throws SQLException{
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));
        product.setType(rs.getString("type"));
        return product;
    }

    // Listar los Articulos
    @Override
    public List<Product> findAll(){
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE + " ORDER BY id";

        try(Connection conn = ConnectionDb.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Product p = new Product(
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock"),
                        rs.getString("type")
                );
                p.setId(rs.getInt("id"));
                products.add(p);
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding article", e);
        }
        return products;
    }

    // Actualizar datos de un Articulo existente
    @Override
    public boolean update(Product product){
        String sql = "UPDATE " + TABLE + " SET name = ?, price = ?, stock = ?, type = ? WHERE id = ?";
        try(Connection connection = ConnectionDb.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getType());
            ps.setInt(5, product.getId());

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            throw new RuntimeException("Error updating article", e);
        }
    }

    // Eliminar Articulo por su codigo
    @Override
    public boolean delete(int id){
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        try(Connection connection = ConnectionDb.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException("Error deleting article", e);
        }
    }


}
