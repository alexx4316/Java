package controller;

import errors.UnauthorizedException;
import model.Partner;
import model.User;
import service.LoginService;
import view.PartnerView;
import view.UserView;

import javax.swing.*;
import java.sql.SQLException;

public class AuthController {
    private final LoginService loginService = new LoginService();

    public void login(String email, String password) {
        try {
            User user = loginService.login(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "Welcome, " + user.getName());

                if (user.getRole().equals("user")) {
                    showUserMenu();
                } else if (user.getRole().equals("partner")) {
                }
            } else {
                throw new UnauthorizedException("Incorrect credentials.");
            }
        } catch (UnauthorizedException e) {
            JOptionPane.showMessageDialog(null, "Authentication error: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    private void showUserMenu(){
        UserController userController = new UserController();
        UserView userView = new UserView(userController);
        userView.showMenu();
    }

    private void showPartnerMenu(){
        PartnerController controller = new PartnerController();
        PartnerView partnerView = new PartnerView(controller);
        partnerView.showMenu();
    }
}
