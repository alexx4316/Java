package Service;

import Dao.UserDao;
import Interfaces.AutenticableInterface;
import Interfaces.UserInterface;
import Model.User;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class UserService implements UserInterface, AutenticableInterface{
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = new UserDao();
    }

    // Registrar un usuario
    @Override
    public void addUser(User user) throws SQLException {
        userDao.addUser(user);
    }

    // Buscar por email
    @Override
    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }

    // Listar todos los usuarios
    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    @Override
    public User authenticate(String email, String password){
        User user = findByEmail(email);
        if (user == null){
            JOptionPane.showMessageDialog(null, " There is no user with this email.");
            return null;
        }

        // Validamos si esta bloqueado
        if (!user.isActive()){
            JOptionPane.showMessageDialog(null, "The user is blocked");
            return null;
        }

        // Validamos contraseña
        if (!user.getPassword().equals(password)){
            JOptionPane.showMessageDialog(null, "Incorrect password");
            return null;
        }

        JOptionPane.showMessageDialog(null, "Welcome" + user.getName());
        return user;
    }

    // Bloquear usuario por email
    public boolean blockUserByEmail(String email) throws SQLException {
        User user = userDao.findByEmail(email);
        if (user == null) return false;
        return userDao.updateStatus(email, false); // tu DAO ya devuelve boolean
    }
}
