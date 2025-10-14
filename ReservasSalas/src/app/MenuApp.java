package app;

import domain.Reservation;
import domain.Room;
import service.ReservationService;
import service.RoomService;
import errors.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuApp {

    private final RoomService roomService = new RoomService();
    private final ReservationService reservationService = new ReservationService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void start() {
        int option = -1;

        do {
            String menu = """
                    === ROOM & RESERVATION MANAGER ===
                    1. Create Room
                    2. List Rooms
                    3. Delete Room
                    4. Create Reservation
                    5. List Reservations by Date Range
                    6. Find Reservation by ID
                    7. Cancel Reservation
                    0. Exit
                    """;

            String input = JOptionPane.showInputDialog(null, menu, "Menu", JOptionPane.PLAIN_MESSAGE);
            if (input == null) break;

            try {
                option = Integer.parseInt(input);

                switch (option) {
                    case 1 -> createRoom();
                    case 2 -> listRooms();
                    case 3 -> deleteRoom();
                    case 4 -> createReservation();
                    case 5 -> listReservationsByRange();
                    case 6 -> findReservation();
                    case 7 -> cancelReservation();
                    case 0 -> JOptionPane.showMessageDialog(null, "Exiting program...");
                    default -> JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
                }

            } catch (BadRequestException e) {
                JOptionPane.showMessageDialog(null, "[400] " + e.getMessage());
            } catch (NotFoundException e) {
                JOptionPane.showMessageDialog(null, "[404] " + e.getMessage());
            } catch (ConflictException e) {
                JOptionPane.showMessageDialog(null, "[409] " + e.getMessage());
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(null, "[500] " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "[500] Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }

        } while (option != 0);
    }

    // ------------------ ROOM ------------------

    private void createRoom() {
        String name = JOptionPane.showInputDialog("Room name:");
        if (name == null) return;

        String capStr = JOptionPane.showInputDialog("Capacity:");
        if (capStr == null) return;

        String availStr = JOptionPane.showInputDialog("Is available? (true/false):");
        if (availStr == null) return;

        try {
            int capacity = Integer.parseInt(capStr);
            boolean available = Boolean.parseBoolean(availStr);

            Room room = new Room(0, name, capacity, available);
            roomService.createRoom(room);
            JOptionPane.showMessageDialog(null, "Room created successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
        }
    }

    private void listRooms() {
        List<Room> rooms = roomService.listAll();
        if (rooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No rooms available.");
            return;
        }

        StringBuilder sb = new StringBuilder("--- ROOM LIST ---\n\n");
        for (Room r : rooms) {
            sb.append("ID: ").append(r.getId())
                    .append(" | Name: ").append(r.getName())
                    .append(" | Capacity: ").append(r.getCapacity())
                    .append(" | Available: ").append(r.isAvailable())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void deleteRoom() {
        String idStr = JOptionPane.showInputDialog("Enter room ID to delete:");
        if (idStr == null) return;

        try {
            int id = Integer.parseInt(idStr);
            Room room = roomService.findById(id);

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete room \"" + room.getName() + "\"?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                roomService.deleteRoom(id);
                JOptionPane.showMessageDialog(null, "Room deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID.");
        }
    }

    // ------------------ RESERVATION ------------------

    private void createReservation() {
        try {
            String roomStr = JOptionPane.showInputDialog("Room ID:");
            if (roomStr == null) return;
            int roomId = Integer.parseInt(roomStr);

            String dateStr = JOptionPane.showInputDialog("Date (yyyy-MM-dd HH:mm):");
            if (dateStr == null) return;
            LocalDateTime date = LocalDateTime.parse(dateStr, formatter);

            String startStr = JOptionPane.showInputDialog("Start (yyyy-MM-dd HH:mm):");
            if (startStr == null) return;
            LocalDateTime start = LocalDateTime.parse(startStr, formatter);

            String endStr = JOptionPane.showInputDialog("End (yyyy-MM-dd HH:mm):");
            if (endStr == null) return;
            LocalDateTime end = LocalDateTime.parse(endStr, formatter);

            Reservation reservation = new Reservation(0, roomId, date, start, end);
            reservationService.createReservation(reservation);
            JOptionPane.showMessageDialog(null, "Reservation created successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd HH:mm");
        }
    }

    private void listReservationsByRange() {
        try {
            String startStr = JOptionPane.showInputDialog("Start (yyyy-MM-dd HH:mm):");
            if (startStr == null) return;
            LocalDateTime start = LocalDateTime.parse(startStr, formatter);

            String endStr = JOptionPane.showInputDialog("End (yyyy-MM-dd HH:mm):");
            if (endStr == null) return;
            LocalDateTime end = LocalDateTime.parse(endStr, formatter);

            List<Reservation> list = reservationService.listByRange(start, end);
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No reservations found for that range.");
                return;
            }

            StringBuilder sb = new StringBuilder("--- RESERVATIONS ---\n\n");
            for (Reservation r : list) {
                sb.append("ID: ").append(r.getId())
                        .append(" | Room: ").append(r.getRoomId())
                        .append(" | Date: ").append(r.getDate())
                        .append(" | Start: ").append(r.getStart())
                        .append(" | End: ").append(r.getEnd())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd HH:mm");
        }
    }

    private void findReservation() {
        String idStr = JOptionPane.showInputDialog("Reservation ID:");
        if (idStr == null) return;

        try {
            int id = Integer.parseInt(idStr);
            Reservation r = reservationService.findById(id);

            String msg = "ID: " + r.getId() +
                    "\nRoom: " + r.getRoomId() +
                    "\nDate: " + r.getDate() +
                    "\nStart: " + r.getStart() +
                    "\nEnd: " + r.getEnd();

            JOptionPane.showMessageDialog(null, msg);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID format.");
        }
    }

    private void cancelReservation() {
        String idStr = JOptionPane.showInputDialog("Reservation ID to cancel:");
        if (idStr == null) return;

        try {
            int id = Integer.parseInt(idStr);
            Reservation r = reservationService.findById(id);

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to cancel reservation #" + r.getId() + " for room " + r.getRoomId() + "?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                reservationService.cancelReservation(id);
                JOptionPane.showMessageDialog(null, "Reservation cancelled successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID.");
        }
    }
}
