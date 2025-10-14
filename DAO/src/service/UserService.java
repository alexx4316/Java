package service;

import dao.UserDAO;
import model.User;
import util.FileUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;
    private final Connection connection;

    public UserService(UserDAO userDAO, Connection connection) {
        this.userDAO = userDAO;
        this.connection = connection;
    }

    public void userRegister(User user) throws Exception {
        if(user.getName() == null || user.getName().trim().isEmpty()){
            throw new Exception("The name cannot be empty");
        }
        if (user.getAge() <=0){
            throw new Exception("The age cannot be negative");
        }

        try {
            connection.setAutoCommit(false); // inicio transacción manual
            // guardar en BD (el DAO hará el INSERT y debería devolver el id generado)
            userDAO.save(user);
            connection.commit();
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ex) { /* log si quieres */ }
            throw new Exception("Error saving in database: " + e.getMessage(), e);
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ex) { /* noop */ }
        }

        // guardar en archivo
        try {
            FileUtil.saveFile(user);
        } catch (IOException e) {
            // compensación: si falla el archivo, eliminamos el registro en BD
            try {
                userDAO.deleteById(user.getId());
            } catch (SQLException ex) {
                throw new Exception("It could not be saved to file and the compensation in BD also failed: " + ex.getMessage(), e);
            }
            throw new Exception("Could not be saved to file. Reverse DB insertion.", e);
        }
    }
}

