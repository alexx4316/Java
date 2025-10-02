package App;

import Dao.UserDao;
import Model.Admin;
import Model.Client;
import Model.User;
import Service.UserService;

import javax.swing.*;
import java.sql.SQLException;

public class MenuApp {
    private final UserService userService;

    public MenuApp() {
        UserDao userDao = new UserDao();
        this.userService = new UserService(userDao);
    }

    public void start(){
        boolean running = true;

        while (running){
            String[] opt = {"Login", "Register", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Select an option",
                    "Main menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opt,
                    opt[0]
            );

            switch (choice){
                case 0 -> loginMenu();
                case 1 -> registerClient();
                case 2 -> running = false;
            }
        }
    }

    // logeo de usuarios
    private void loginMenu() {
        String email = JOptionPane.showInputDialog(null, "Enter email: ");
        String password = JOptionPane.showInputDialog(null, "Enter password: ");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email and password are required");
            return;
        }

        User user = userService.authenticate(email.trim(), password);
        if (user == null) return;
        if (user instanceof Admin admin) adminMenu(admin);
        else if (user instanceof Client client) clientMenu(client);
    }

    // Registro de clientes
    private void registerClient(){
        String name = JOptionPane.showInputDialog(null, "Enter name: ");
        String email = JOptionPane.showInputDialog(null, "Enter email: ");
        String password = JOptionPane.showInputDialog(null, "Enter password: ");
        String address = JOptionPane.showInputDialog(null, "Enter address: ");
        String phone = JOptionPane.showInputDialog(null, "Enter phone: ");
        double balance = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter initial balance"));

        try{
            userService.addUser(new Client(name, email, password, "Client", balance, address, phone));
            JOptionPane.showMessageDialog(null, "Client register successfully");
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error registering client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Vista y opciones perfil admin
    private void adminMenu(Admin admin){
        boolean back = false;
        while (!back){
            String[] opt = {"Views users", "Block user", "Unblock user", "back"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "admin menu",
                    "admin options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opt,
                    opt[0]
            );
            switch (choice){
                case 0 -> {
                    StringBuilder stringBuilder = new StringBuilder("=== Users ===\n");
                    try {
                        for (User u : userService.getAllUsers()) {
                            stringBuilder.append(u.getName())
                                    .append(" - ").append(u.getEmail())
                                    .append(" - ").append(u.getRol())
                                    .append(" - ").append(u.isActive() ? "Active" : "Blocked")
                                    .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, stringBuilder.toString());
                    } catch (SQLException e){
                        JOptionPane.showMessageDialog(null, "Error fetching user" + e.getMessage());
                        e.printStackTrace();
                    }
                }
                case 1 -> {
                    String email = JOptionPane.showInputDialog(null, "Enter email of user to block: ");
                    if (email == null || email.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Email is required");
                        break;
                    }
                    email = email.trim();

                    try {
                        boolean ok = userService.blockUserByEmail(email);
                        if (ok) {
                            JOptionPane.showMessageDialog(null, "User blocked");
                        } else {
                            JOptionPane.showMessageDialog(null, "User not found or not updated");
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error blocking user: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    String email = JOptionPane.showInputDialog(null, "Enter emal or user to unblock: ");
                    User user = userService.findByEmail(email);
                    if (user != null){
                        user.setActivo(true);
                        JOptionPane.showMessageDialog(null, "User unbloked");
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found");
                    }
                }
                case 3 -> back = true;
            }
        }
    }

    private void clientMenu(Client client){
        boolean back = false;
        while (!back){
            String[] opt = {"View profile", "Update data", "Make purchase"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "CLient menu",
                    "Client options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opt,
                    opt[0]
            );

            switch (choice){
                case 0 -> client.viewProfile();
                case 1 -> updateDataClient(client);
                case 2 -> back = true;
            }
        }
    }

    private void updateDataClient(Client client){
        String newAddress = JOptionPane.showInputDialog(null, "Enter new address: ");
        if (newAddress != null && !newAddress.trim().isEmpty()){
            client.setAddress(newAddress);
        }

        String newPhone = JOptionPane.showInputDialog(null, "Enter new phone: ");
        if (newPhone != null && !newPhone.trim().isEmpty()){
            client.setPhone(newPhone);
        }

        String newBalanceStr = JOptionPane.showInputDialog(null, "Enter new balance");
        if (newBalanceStr != null && !newBalanceStr.trim().isEmpty()){
            try{
                double newBalance = Double.parseDouble(newBalanceStr);
                client.setBalance(newBalance);
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Balance must be a valid number");
            }
        }
        JOptionPane.showMessageDialog(null, "Data updated successfully");
    }
}