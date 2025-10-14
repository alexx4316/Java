package controller;

import domain.User;
import errors.BadRequestException;
import errors.ServiceException;
import errors.UnauthorizedException;
import service.AuthService;

import javax.swing.*;

public class AuthController {

    private final AuthService authService = new AuthService();

    public void login(){

        // Pedida de datos para logearse
        String email = JOptionPane.showInputDialog(null, "Enter your email address: ");
        String password = JOptionPane.showInputDialog(null, "Enter your password: ");
        try{
            User user = authService.login(email,password);
            JOptionPane.showMessageDialog(
                    null,
                    "Welcome, " + user.getEmail() + "\nRol: " + user.getRole(),
                    "Successful login",
                    JOptionPane.INFORMATION_MESSAGE
            );
        // Multi catch para diferentes posibles errores
        } catch (BadRequestException e){
            JOptionPane.showMessageDialog(
                    null,
                    "Error 400 - Invalid request: " + e.getMessage(),
                    "Validation error",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (UnauthorizedException e){
            JOptionPane.showMessageDialog(
                    null,
                    "Error 401 - Unauthorized: " + e.getMessage(),
                    " Authentication error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (ServiceException e){
            JOptionPane.showMessageDialog(
                    null,
                    "Error 500 - Internal server error: " + e.getMessage(),
                    "System Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e){
            JOptionPane.showMessageDialog(
                    null,
                    "Unexpected error: " + e.getMessage(),
                    "Error",
                    JOptionPane.PLAIN_MESSAGE
            );
        } finally {
            JOptionPane.showMessageDialog(
                    null,
                    "Operation completed",
                    "System",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
    }

    public void logout(){
        authService.logout();
        JOptionPane.showMessageDialog(null, "Successfully logged out");
    }

    public void viewUser(){
        User user = authService.getCurrentUSer();
        if (user != null){
            JOptionPane.showMessageDialog(
                    null,
                    "User:\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Role: " + user.getRole(),
                    "Active session",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(null, "There is no active user currently");
        }
    }
}
