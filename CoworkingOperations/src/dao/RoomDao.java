package dao;

import domain.Room;
import errors.DataAccessException;

import java.util.List;

public interface RoomDao {
    void create(Room room) throws DataAccessException;
    Room findByName(String name) throws DataAccessException;
    List<Room> findAll() throws DataAccessException;
    void update(Room room) throws DataAccessException;
    void delete(int id) throws DataAccessException;
}
