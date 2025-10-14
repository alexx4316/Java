package dao;

import domain.Reservation;
import errors.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDao {
    // Metodos
    void create(Reservation reservation) throws DataAccessException;
    Reservation searchById(int id) throws DataAccessException;
    List<Reservation> list() throws DataAccessException;
    void delete(int id) throws DataAccessException;
    List<Reservation> listByRange(LocalDateTime start, LocalDateTime end) throws DataAccessException;
}
