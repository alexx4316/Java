package dao;

import domain.Room;
import errors.DataAccessException;

import java.util.List;

public interface RoomDao {
    // Metodos
    void insert(Room room) throws DataAccessException;
    void update(Room room) throws DataAccessException;
    void delete(int id) throws DataAccessException;
    Room findById(int id) throws DataAccessException;
    Room findByName(String name) throws DataAccessException;
    List<Room> findAll() throws DataAccessException;
}
