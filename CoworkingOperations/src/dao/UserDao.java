package dao;

import domain.User;
import errors.DataAccessException;

public interface UserDao {
    User findByEmail(String email) throws DataAccessException;
}
