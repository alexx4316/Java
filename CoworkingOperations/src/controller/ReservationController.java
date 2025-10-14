package controller;

import domain.User;
import errors.BadRequestException;
import errors.DataAccessException;
import errors.UnauthorizedException;
import service.AuthService;
import service.ReservationService;

import javax.swing.*;

public class ReservationController {

    private final ReservationService reservationService = new ReservationService();
    private final AuthService authService = new AuthService();

    public void bookRoom(){
        try {
            User current = authService.getCurrentUSer();
            if (current == null){
                throw new UnauthorizedException("you must be logged in to make a reservations");
            }

            // Pedimos los datos para la creacion de la reserva
            String roomName = JOptionPane.showInputDialog(null, "Enter room name:");
            String date = JOptionPane.showInputDialog(null, "Enter date (YYYY-MM-DD):");
            String startTime = JOptionPane.showInputDialog(null, "Enter start time (HH:MM):");
            String endTime = JOptionPane.showInputDialog(null, "Entre end time (HH:MM):");

            if (roomName == null || roomName.trim().isEmpty() ||
            date == null || date.trim().isEmpty() ||
            startTime == null || startTime.trim().isEmpty() ||
            endTime == null || endTime.trim().isEmpty()){
                throw new BadRequestException("All fields are required.");
            }

            reservationService.createReservation(current,roomName,date,startTime,endTime);
            JOptionPane.showMessageDialog(null, "Reservation created successfully");
        } catch (BadRequestException e){
            JOptionPane.showMessageDialog(null, "Validation Error " + e.getMessage(), " Error 400", JOptionPane.WARNING_MESSAGE);
        } catch (UnauthorizedException e){
            JOptionPane.showMessageDialog(null, "Unauthorized " + e.getMessage(), " Error 401", JOptionPane.ERROR_MESSAGE);
        } catch (DataAccessException e){
            JOptionPane.showMessageDialog(null, "Database Error " + e.getMessage(), " Error 500", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unexpected Error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viewMyReservations(){
        try {
            User current = authService.getCurrentUSer();
            if (current == null){
                throw new UnauthorizedException("You must be logged in to view your reservations.");
            }

            String reservation = reservationService.getReservationsByUser(current);
            JOptionPane.showMessageDialog(null, reservation.isEmpty() ? "No reservations found." : reservation);
        } catch (UnauthorizedException e){
            JOptionPane.showMessageDialog(null, "Unauthorized " + e.getMessage(), " Error 401", JOptionPane.ERROR_MESSAGE);
        } catch (DataAccessException e){
            JOptionPane.showMessageDialog(null, "Database Error " + e.getMessage(), " Error 500", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unexpected Error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void reportIncident(){
        try{
            User current = authService.getCurrentUSer();
            if (current == null){
                throw new UnauthorizedException("You must be logged in to report an incident.");
            }

            String title = JOptionPane.showInputDialog(null, "Enter incident title:");
            String description = JOptionPane.showInputDialog(null, "Enter description:");
            String room = JOptionPane.showInputDialog(null, "Enter room (optional):");
            String category = JOptionPane.showInputDialog(null, "Enter category:");

            if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()){
                throw new BadRequestException("Title and description are required");
            }

            JOptionPane.showMessageDialog(null, "Incident reported successfully (stub).");

        } catch (BadRequestException e){
            JOptionPane.showMessageDialog(null, "Validation Error " + e.getMessage(), " Error 400", JOptionPane.WARNING_MESSAGE);
        } catch (UnauthorizedException e){
            JOptionPane.showMessageDialog(null, "Unauthorized " + e.getMessage(), " Error 401", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unexpected Error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viewAllReservations(){
        try {
            User current = authService.getCurrentUSer();
            if (current == null){
                throw new UnauthorizedException("You must be logged in to view reservations.");
            }
            if (!current.getRole().name().equalsIgnoreCase("STAFF")){
                throw new UnauthorizedException("Only STAFF members can view all reservations.");
            }
            String allReservations = reservationService.getAllReservations();
            JOptionPane.showMessageDialog(
                    null,
                    allReservations.isEmpty() ? "No reservations found" : allReservations,
                    "All Reservations",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (UnauthorizedException e){
            JOptionPane.showMessageDialog(null, "Unauthorized " + e.getMessage(), " Error 401", JOptionPane.ERROR_MESSAGE);
        } catch (DataAccessException e){
            JOptionPane.showMessageDialog(null, "Database Error " + e.getMessage(), " Error 500", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unexpected Error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancelReservation(){
        try{
            User current = authService.getCurrentUSer();
            if (current == null){
                throw new UnauthorizedException("You must be logged in to cancel a reservation.");
            }

            String reservationIdStr = JOptionPane.showInputDialog(null, "Enter reservation Id to cancel:");
            if (reservationIdStr == null || reservationIdStr.trim().isEmpty()){
                throw new BadRequestException("Reservation ID is required.");
            }
            int reservationId;
            try{
                reservationId = Integer.parseInt(reservationIdStr.trim());
            } catch (NumberFormatException e){
                throw new BadRequestException("Reservation ID must be a valid number");
            }
            reservationService.cancelReservation(current, reservationId);
            JOptionPane.showMessageDialog(null, "Reservation cancelled successfully.");
        } catch (BadRequestException e){
            JOptionPane.showMessageDialog(null, "Validation Error " + e.getMessage(), " Error 400", JOptionPane.WARNING_MESSAGE);
        } catch (UnauthorizedException e){
            JOptionPane.showMessageDialog(null, "Unauthorized " + e.getMessage(), " Error 401", JOptionPane.ERROR_MESSAGE);
        } catch (DataAccessException e){
            JOptionPane.showMessageDialog(null, "Database Error " + e.getMessage(), " Error 500", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unexpected Error " + e.getMessage(), " Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
