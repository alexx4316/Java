package service;

import dao.UserDao;
import dao.UserDaoJDBC;
import errors.BadRequestException;
import model.User;
import util.ValidatorUtil;

import java.sql.SQLException;

public class LoginService {
    private final UserDao userDao = new UserDaoJDBC();

    public User login(String email, String password) throws SQLException, BadRequestException {
        // Validaciones básicas
        ValidatorUtil.validateNotNull(email, "The email cannot be null.");
        ValidatorUtil.validateNotNull(password, "The password cannot be null.");

        if (email.isEmpty() || password.isEmpty()) {
            throw new BadRequestException("Email and password cannot be empty.");
        }

        // Validar formato de email
        if (!ValidatorUtil.isValidEmail(email)) {
            throw new BadRequestException("Invalid email format.");
        }

        // Verificar las credenciales con el DAO
        if (userDao.validateLogin(email, password)) {
            return userDao.getByEmail(email);
        } else {
            throw new BadRequestException("Incorrect email or password.");
        }
    }
}
