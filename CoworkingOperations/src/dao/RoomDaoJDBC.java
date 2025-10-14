package dao;

import domain.Room;
import errors.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoJDBC implements RoomDao{

    private final String SQL_INSERT = "INSERT INTO rooms(name, type, price, available) VALUES (?, ?, ?, ?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM rooms";
    private final String SQL_SELECT_BY_NAME = "SELECT * FROM rooms WHERE name = ?";
    private final String SQL_UPDATE = "UPDATE rooms set name=?, type=?, price=?, available=? WHERE id=?";
    private final String SQL_DELETE = "DELETE FROM rooms WHERE id=?";

    private Connection connection;

    public RoomDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Room room) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_INSERT)){
            ps.setString(1, room.getName());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getPrice());
            ps.setBoolean(4, room.isAvailable());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error creating room", e);
        }
    }

    @Override
    public Room findByName(String name) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_NAME)){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Room(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getDouble("price"),
                        rs.getBoolean("available")
                );
            }
        } catch (SQLException e){
            throw new DataAccessException("Error searching for room", e);
        }
        return null;
    }

    @Override
    public List<Room> findAll() throws DataAccessException {
        List<Room> rooms = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                rooms.add(new Room(
                 rs.getInt("id"),
                 rs.getString("name"),
                 rs.getString("type"),
                 rs.getDouble("price"),
                 rs.getBoolean("available")
                ));
            }
        } catch (SQLException e){
            throw new DataAccessException("Error listing rooms", e);
        }
        return rooms;
    }

    @Override
    public void update(Room room) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)){
            ps.setString(1 , room.getName());
            ps.setString(2, room.getType());
            ps.setDouble(3, room.getPrice());
            ps.setBoolean(4, room.isAvailable());
            ps.setInt(5, room.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error updating room", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_DELETE)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error deleting room", e);
        }
    }
}
