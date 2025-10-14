package service;

import dao.ReservationDao;
import dao.ReservationDaoJDBC;
import dao.RoomDao;
import dao.RoomDaoJDBC;
import domain.Reservation;
import domain.Room;
import errors.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {
    private final ReservationDao reservationDao = new ReservationDaoJDBC();
    private final RoomDao roomDao = new RoomDaoJDBC();

    public void createReservation(Reservation reservation){

        // Validacion de datos
        if (reservation == null){
            throw new BadRequestException("The reservation cannot be void.");
        }
        if (reservation.getRoomId() <= 0){
            throw new BadRequestException("Room ID is required.");
        }
        if (reservation.getStart() == null || reservation.getEnd() == null){
            throw new BadRequestException("Start and end dates are mandatory.");
        }
        if (reservation.getStart().isAfter(reservation.getEnd())){
            throw new BadRequestException("The start time cannot be after the end time.");
        }

        try{
            // Verificamos existencia de la sala
            Room room = roomDao.findById(reservation.getRoomId());
            if (room == null){
                throw new NotFoundException("The room with ID " + reservation.getRoomId() + " does not exist.");
            }

            // Verificamos disponibilidad
            List<Reservation> existing = reservationDao.listByRange(
                    reservation.getStart(), reservation.getEnd());

            boolean solapado = existing.stream().anyMatch(r
            -> r.getRoomId() == reservation.getRoomId() &&
                    overlaps(r.getStart(), r.getEnd(), reservation.getStart(), reservation.getEnd()));

            if (solapado){
                throw new ConflictException("The room is already booked in the indicated time range.");
            }

            // Guardamos la reserva
            reservationDao.create(reservation);

        } catch (DataAccessException e) {
            throw new ServiceException("Technical error when creating the reservation.", e);
        } finally {
            JOptionPane.showMessageDialog(null, "Reservation creation operation completed.");
        }
    }

    public Reservation findById(int id){
        try {
            Reservation reservation = reservationDao.searchById(id);
            if (reservation == null){
                throw new NotFoundException("There is no reservation with ID " + id);
            }
            return reservation;
        } catch (DataAccessException e){
            throw new ServiceException("Technical error when checking the reservation", e);
        }
    }

    public void cancelReservation(int id){
        try {
            Reservation existing = reservationDao.searchById(id);
            if (existing == null){
                throw new NotFoundException("The reservation you wish to cancel does not exist.");
            }
            reservationDao.delete(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Technical error when canceling the reservation", e);
        } finally {
            JOptionPane.showMessageDialog(null, "Reservation cancellation operation completed.");
        }
    }

    public List<Reservation> listByRange(LocalDateTime start, LocalDateTime end){
        try {
            if (start == null || end == null || start.isAfter(end)){
                throw new BadRequestException("Invalid date range.");
            }
            return reservationDao.listByRange(start,end);
        } catch (DataAccessException e) {
            throw new ServiceException("Technical error when listing reservations", e);
        } finally {
            JOptionPane.showMessageDialog(null, "Listing operation completed.");
        }
    }

    // Utilidad para validar el solapamiento
    private boolean overlaps(LocalDateTime star1, LocalDateTime end1, LocalDateTime star2, LocalDateTime end2){
        return star1.isBefore(end2) && star2.isBefore(end1);
    }
}
