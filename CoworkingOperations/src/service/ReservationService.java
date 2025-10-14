package service;

import dao.ReservationDao;
import dao.ReservationDaoJDBC;
import dao.RoomDao;
import dao.RoomDaoJDBC;
import domain.Reservation;
import domain.User;
import errors.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {
    private final ReservationDao reservationDao;
    private final RoomDao roomDao;

    public ReservationService(Connection connection){
        this.reservationDao = new ReservationDaoJDBC(connection);
        this.roomDao = new RoomDaoJDBC(connection);
    }

    public void createReservation(User current, int roomId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime) throws BadRequestException, ConflictException, DataAccessException, ServiceException {
        try {
            // Validamos los campos
            if (roomId <= 0 || date == null || startTime == null || endTime == null) {
                throw new BadRequestException("All fields are required.");
            }

            // Validaciones de hora

            if (startTime.compareTo(endTime)>=0){
                throw new BadRequestException("Start time must be before end time");
            }

            boolean overlaps = reservationDao.existsOverLappingReservation(roomId,date,startTime,endTime);
            if (overlaps){
                throw new ConflictException("There is already a reservation for this room in that time range");
            }

            // Creamos la reserva y la guardamos
            Reservation reservation = new Reservation();
            reservation.setUserId(current.getId());
            reservation.setRoomId(roomId);
            reservation.setDate(date);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setStatus("ACTIVE");

            reservationDao.create(reservation);
        } catch (DataAccessException e){
            throw e;
        } catch (Exception e){
            throw new ServiceException("Unexpected error while creating reservation.", e);
        }
    }

    public String getReservationsByUser(User user) throws DataAccessException, ServiceException{
        try {
            List<Reservation> list = reservationDao.findByUserId(user.getId());
            if (list.isEmpty()){
                return "You don't have any reservations yet.";
            }

            StringBuilder sb = new StringBuilder();
            for (Reservation r : list){
                sb.append("ID: ").append(r.getId())
                        .append("| Room: ").append(r.getRoomName())
                        .append(" | Date: ").append(r.getDate())
                        .append(" | ").append(r.getStartTime()).append(" - ").append(r.getEndTime())
                        .append(" | Status: ").append(r.getStatus())
                        .append("\n");
            }
            return sb.toString();
        } catch ( DataAccessException e){
            throw e;
        } catch (Exception e){
            throw new ServiceException("Unexpected error while listing reservations.", e);
        }
    }

    public String getAllReservations() throws DataAccessException, ServiceException{
        try {
            List<Reservation> list = reservationDao.findAll();
            StringBuilder sb = new StringBuilder();
            if (list.isEmpty()){
                return "No reservations found.";
            }
            for (Reservation r : list){
                sb.append("ID: ").append(r.getId())
                        .append("| Room: ").append(r.getRoomName())
                        .append(" | Date: ").append(r.getDate())
                        .append(" | ").append(r.getStartTime()).append(" - ").append(r.getEndTime())
                        .append(" | Status: ").append(r.getStatus())
                        .append("\n");
            }
            return sb.toString();
        } catch ( DataAccessException e){
            throw e;
        } catch (Exception e){
            throw new ServiceException("Unexpected error while listing reservations.", e);
        }
    }

    public void cancelReservation(User current, int reservationId) throws NotFoundException, UnauthorizedException, DataAccessException, ServiceException {
        try {
            Reservation existing = reservationDao.findById(reservationId);
            if (existing == null){
                throw new NotFoundException("Reservation not found.");
            }

            // Validar que solo el creado o el staff pueda eliminar la reserva
            if (current.getRole().name().equalsIgnoreCase("MEMBER") && existing.getUserId() != current.getId()){
                throw new UnauthorizedException("You cannot cancel another user's reservation.");
            }
            reservationDao.cancel(reservationId);
        } catch (DataAccessException e){
            throw e;
        } catch (NotFoundException | UnauthorizedException e){
            throw e;
        } catch (Exception e){
            throw new ServiceException("Unexpected error while cancelling reservation.", e);
        }
    }

    public void reportIncident(User current, String title, String description, String room, String category) throws BadRequestException{
        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()){
            throw new BadRequestException("Title and description are required.");
        }
    }
}
