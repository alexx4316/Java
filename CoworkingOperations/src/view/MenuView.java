package view;

import controller.AuthController;
import controller.IncidentController;
import controller.ReservationController;
import domain.Role;
import domain.User;
import service.AuthService;

import javax.swing.*;

public class MenuView {

    private static final AuthController authcontroller = new AuthController();
    private static final AuthService authservice = new AuthService();
    private static final IncidentController incidentController = new IncidentController();
    private static final ReservationController reservationController = new ReservationController();

    // Inicio de menu
    public void start(){
        boolean running = true;
        while (running){
            String[] options = {"Login","View session","Logout","Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to the Coworking Portal",
                    "Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice){
                case 0 -> Login();
                case 1 -> ViewSession();
                case 2 -> Logout();
                case 3, JOptionPane.CLOSED_OPTION -> running = false;
                default -> running = false;
            }
        }
        JOptionPane.showMessageDialog(null, "Program completed");
    }

    // Login y validacion de rol
    private static void Login(){
        authcontroller.login();
        User current = authservice.getCurrentUSer();

        if (current != null){
            if (current.getRole() == Role.MEMBER){
                menuMember();
            } else if (current.getRole() == Role.STAFF){
                menuStaff();
            }
        }
    }

    // Ver la seccion acual STAFF/MEMBER
    private static void ViewSession(){
        authcontroller.viewUser();
    }

    // Funcion de cerrar seccion
    private static void Logout(){
        authcontroller.logout();
    }

    // Menu para el rol de members
    private static void menuMember(){
        boolean inmenu = true;

        while (inmenu){
            String[] options = {"Book a room","View my reservations","Cancel reservation","Report an incident","Logout"};
            int selection = JOptionPane.showOptionDialog(
                    null,
                    "Menu MEMBERS",
                    "Options MEMBER",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (selection){
                case 0 -> reservationController.bookRoom();
                case 1 -> reservationController.viewMyReservations();
                case 2 -> reservationController.cancelReservation();
                case 3 -> reservationController.reportIncident();
                case 4, JOptionPane.CLOSED_OPTION -> inmenu = false;
            }
        }
    }

    // Menu para el rol de staff
    private static void menuStaff(){
        boolean inmenu = true;

        while (inmenu){
            String[] options = {"See reservations","View incidents","Manage maintenance","Logout"};
            int selection = JOptionPane.showOptionDialog(
                    null,
                    "Panel of STAFF",
                    "Options STAFF",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (selection){
                case 0 -> reservationController.viewAllReservations();
                case 1 -> incidentController.viewIncident();
                case 2 -> incidentController.manageMaintenance();
                case 3, JOptionPane.CLOSED_OPTION -> inmenu = false;
            }
        }
    }
}
