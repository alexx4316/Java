package Service;

import Interfaces.AutenticableInterface;
import Interfaces.UserInterface;
import Model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserInterface, AutenticableInterface {
    private final List<User> users;

    public UserService(){
        this.users = new ArrayList<>();
    }

    // Registrar un usuario
    @Override
    public void addUser(User user){
        users.add(user);
    }

    // Buscar por email
    @Override
    public User findByEmail(String email){
        for (User u : users){
            if (u.getEmail().equalsIgnoreCase(email)){
                return u;
            }
        }
        return null;
    }

    // Listar todos los usuarios
    @Override
    public List<User> getAllUsers(){
        return users;
    }

    @Override
    public User authenticate(String email, String password) {
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
}
