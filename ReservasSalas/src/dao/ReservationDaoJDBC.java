package dao;

import domain.Reservation;
import errors.DataAccessException;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoJDBC implements ReservationDao{
    @Override
    public void create(Reservation reservation) throws DataAccessException {
        String sql = "INSERT INTO reservations (room_id, date, start_time, end_time) VALUES (?, ?, ?, ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, reservation.getRoomId());
            ps.setTimestamp(2, Timestamp.valueOf(reservation.getDate()));
            ps.setTimestamp(3, Timestamp.valueOf(reservation.getStart()));
            ps.setTimestamp(4, Timestamp.valueOf(reservation.getEnd()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
    }

    @Override
    public Reservation searchById(int id) throws DataAccessException {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Reservation(
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
        return null;
    }

    @Override
    public List<Reservation> list() throws DataAccessException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime()
                ));
            }

        } catch (SQLException e){
            throw new DataAccessException("Error accessing the database", e);
        }
        return reservations;
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
    }

    @Override
    public List<Reservation> listByRange(LocalDateTime start, LocalDateTime end) throws DataAccessException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE NOT (end_time <= ? OR start_time >= ?)";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getTimestamp("start_time").toLocalDateTime(),
                        rs.getTimestamp("end_time").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing the database", e);
        }
        return reservations;
    }
}
