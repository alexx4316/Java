package service;

import dao.BookDaoJDBC;
import dao.DBConnection;
import dao.UserDao;
import dao.UserDaoJDBC;
import errors.BadRequestException;
import errors.UnauthorizedException;
import model.User;
import util.ValidatorUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {

    private final UserDao userDao;
    public UserService() {
        try {
            Connection connection = DBConnection.getConnection();
            this.userDao = new UserDaoJDBC(connection);
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to database: " + e.getMessage());
        }
    }

    // Crear un nuevo usuario
    public void createUser(User user) throws SQLException, BadRequestException {
        // Validaciones de nulos
        ValidatorUtil.validateNotNull(user, "The user cannot be null.");
        ValidatorUtil.validateNotNull(user.getEmail(), "The email cannot be null.");
        ValidatorUtil.validateNotNull(user.getPassword(), "The password cannot be null.");

        // Validar formato de email
        if (!ValidatorUtil.isValidEmail(user.getEmail())) {
            throw new BadRequestException("The email format is invalid.");
        }

        // Validar que la contraseña no esté vacía
        if (user.getPassword().isEmpty()) {
            throw new BadRequestException("The password cannot be empty.");
        }

        // Crear el usuario si pasa las validaciones
        userDao.create(user);
    }

    // Validar login de usuario
    public User login(String email, String password) throws SQLException, BadRequestException, UnauthorizedException {
        // Validaciones de entrada
        ValidatorUtil.validateNotNull(email, "The email cannot be null.");
        ValidatorUtil.validateNotNull(password, "The password cannot be null.");

        if (email.isEmpty() || password.isEmpty()) {
            throw new BadRequestException("Email and password cannot be empty.");
        }

        if (!ValidatorUtil.isValidEmail(email)) {
            throw new BadRequestException("Invalid email format.");
        }

        // Verificar credenciales
        if (userDao.validateLogin(email, password)) {
            return userDao.getByEmail(email);
        } else {
            throw new UnauthorizedException("Incorrect credentials.");
        }
    }
}
