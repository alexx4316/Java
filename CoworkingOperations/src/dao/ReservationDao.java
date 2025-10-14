package dao;

import domain.Reservation;
import errors.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationDao {

    // Crear una nueva reserva
    void create(Reservation reservation) throws DataAccessException;

    // Buscar reservas por usuario
    List<Reservation> findByUserId(int userId) throws DataAccessException;

    // Obtener todas las reservas
    List<Reservation> findAll() throws DataAccessException;

    // Verificar si una habitacion esta disponible
    boolean existsOverLappingReservation(int roomId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime) throws DataAccessException;

    // Cancelamos una reserva
    void cancel(int reservationId) throws DataAccessException;

    // Buscamos por id
    Reservation findById(int reservationId) throws DataAccessException;
}
