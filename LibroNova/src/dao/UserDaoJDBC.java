package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJDBC implements UserDao{
    private final String SQL_CREATE = "INSERT INTO users (name, email, role, password) VALUES (?, ?, ?, ?)";
    private final String SQL_VALIDATION = "SELECT * FROM users WHERE email = ? AND password = ?";
    private final String SQL_GET_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

    private Connection connection;
    public UserDaoJDBC(Connection connection){
        this.connection = connection;
    }

    public UserDaoJDBC() {

    }

    @Override
    public void create(User user) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getPassword());
            ps.executeQuery();
        } catch (SQLException e){
            throw new SQLException("Error creating user", e);
        }
    }

    @Override
    public User getByEmail(String email) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_EMAIL)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e){
            throw new SQLException("Error finding user", e);
        }
        return null;
    }

    @Override
    public boolean validateLogin(String email, String password) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_VALIDATION)){
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            throw new SQLException("Error validating login", e);
        }
    }
}
