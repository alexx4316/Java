package dao;

import domain.Reservation;
import errors.DataAccessException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoJDBC implements ReservationDao {

    private final String SQL_INSERT = "INSERT INTO reservations(room_id, user_id, date, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_SELECT_ALL = "SELECT * FROM reservations";
    private final String SQL_SELECT_BY_ID_USER = "SELECT * FROM reservations WHERE user_id = ?";
    private final String SQL_SELECT_BY_ID_RESERVATION = "SELECT * FROM reservations WHERE id = ?";
    private final String SQL_CANCELED = "UPDATE reservations SET status = 'CANCELLED' WHERE id = ?";
    private final String SQL_OVER_LAPPING = "SELECT COUNT(*) FROM reservations WHERE room_id = ? AND date = ? AND ((start_time < ? AND end_time > ?) OR (start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?))";

    private final Connection connection;

    public ReservationDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Reservation reservation) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_INSERT)){
            ps.setInt(1, reservation.getRoomId());
            ps.setInt(2, reservation.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(reservation.getDate()));
            ps.setTimestamp(4, Timestamp.valueOf(reservation.getStartTime()));
            ps.setTimestamp(5, Timestamp.valueOf(reservation.getEndTime()));
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error creating reservation", e);
        }
    }

    @Override
    public List<Reservation> findByUserId(int userId) throws DataAccessException {
        List<Reservation> reservations = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID_USER)){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                reservations.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving user reservations", e);
        }
        return reservations;
    }

    @Override
    public List<Reservation> findAll() throws DataAccessException {
        List<Reservation> reservations = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ALL);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                reservations.add(mapResultSet(rs));
            }
        }
        catch (SQLException e){
            throw new DataAccessException("Error retrieving reservations", e);
        }
        return reservations;
    }

    @Override
    public boolean existsOverLappingReservation(int roomId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_OVER_LAPPING)){
            ps.setInt(1, roomId);
            ps.setTimestamp(2, Timestamp.valueOf(date));
            ps.setTimestamp(3, Timestamp.valueOf(endTime)); // start_time < endTime
            ps.setTimestamp(4, Timestamp.valueOf(startTime)); // end_time > startTime
            ps.setTimestamp(5, Timestamp.valueOf(startTime));
            ps.setTimestamp(6, Timestamp.valueOf(endTime));
            ps.setTimestamp(7, Timestamp.valueOf(startTime));
            ps.setTimestamp(8, Timestamp.valueOf(endTime));
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e){
            throw new DataAccessException("Error checking overlapping reservation", e);
        }
    }

    @Override
    public void cancel(int reservationId) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_CANCELED)){
            ps.setInt(1, reservationId);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error deleting reservation", e);
        }

    }

    @Override
    public Reservation findById(int reservationId) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_SELECT_BY_ID_RESERVATION)){
            ps.setInt(1, reservationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving reservations", e);
        }
        return null;
    }

    private Reservation mapResultSet(ResultSet rs) throws SQLException {
        Reservation r = new Reservation();
        r.setId(rs.getInt("id"));
        r.setRoomId(rs.getInt("room_id"));
        r.setUserId(rs.getInt("user_id"));
        r.setDate(rs.getTimestamp("date").toLocalDateTime());
        r.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        r.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
        r.setStatus(rs.getString("status"));
        r.setIncidentDescription(rs.getString("incident_description"));
        return r;
    }
}
