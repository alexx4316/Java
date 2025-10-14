package service;

import dao.UserDao;
import dao.UserDaoJDBC;
import domain.User;
import errors.BadRequestException;
import errors.DataAccessException;
import errors.ServiceException;
import errors.UnauthorizedException;

public class AuthService {
    private final UserDao userDao = new UserDaoJDBC();
    private static User currentUSer;

    public User login(String email, String password) throws BadRequestException, UnauthorizedException, ServiceException{
        if (email == null || email.isBlank() || password == null || password.isBlank()){
            throw new BadRequestException("Fields cannot be empty");
        }

        try {
            User user = userDao.findByEmail(email);
            if (user == null){
                throw new UnauthorizedException("User not found");
            }
            if (!user.getPassword().equals(password)){
                throw new UnauthorizedException("Incorrect password");
            }

            currentUSer = user;
            return user;
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when accessing data", e);
        }
    }

    public User getCurrentUSer(){
        return currentUSer;
    }

    public void logout(){
        currentUSer = null;
    }

}
