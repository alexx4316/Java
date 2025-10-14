package dao;

import domain.Room;
import errors.DataAccessException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoJDBC implements RoomDao{
    @Override
    public void insert(Room room) {
        String sql = "INSERT INTO rooms (name, capacity, available) VALUES (?,?,?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, room.getName());
            ps.setInt(2, room.getCapacity());
            ps.setBoolean(3, room.isAvailable());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
    }

    @Override
    public void update(Room room) {
        String sql = "UPDATE rooms SET name = ?, capacity = ?, available = ? WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, room.getName());
            ps.setInt(2, room.getCapacity());
            ps.setBoolean(3, room.isAvailable());
            ps.setInt(4, room.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM rooms WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
    }

    @Override
    public Room findById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
        return null;
    }

    @Override
    public Room findByName(String name) throws DataAccessException {
        String sql = "SELECT * FROM rooms WHERE name = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new Room(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("capacity"),
                            rs.getBoolean("available")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getBoolean("available")
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
        return rooms;
    }
}
