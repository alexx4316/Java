package dao;

import domain.Role;
import domain.User;
import errors.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoJDBC implements UserDao {

    private final String SQL_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email=?";

    private Connection connection;

    public void userDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public User findByEmail(String email) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_EMAIL)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Role role = Role.valueOf(rs.getString("role").toUpperCase());
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        role
                );
            }
        } catch (SQLException e){
            throw new DataAccessException("Error accessing the database", e);
        }
        return null;
    }
}
