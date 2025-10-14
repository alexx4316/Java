package menu;

import model.User;
import service.UserService;

import javax.swing.*;
import java.util.Scanner;

public class MenuConsole {
    private final UserService userService;

    public MenuConsole(UserService userService){
        this.userService = userService;
    }

    public void show(){
        int opt;
        do {
            String menu = """
                    === Menu ===
                    1. Register
                    2. Exit
                    """;
            String input = JOptionPane.showInputDialog(menu);
            if (input == null){
                opt = 2;
            } else {
                try {
                    opt = Integer.parseInt(input);
                } catch (NumberFormatException e){
                    opt =-1;
                }
            }
            switch (opt){
                case 1 -> addUser();
                case 2 -> JOptionPane.showMessageDialog(null, "exit..");
                case 3 -> JOptionPane.showMessageDialog(null,"Invalid option");
            }
        } while (opt != 2);
    }

    private void addUser(){
        try {
            String name = JOptionPane.showInputDialog("Enter name: ");
            if (name == null) return;

            String ageStr = JOptionPane.showInputDialog("Enter age: ");
            if (ageStr == null) return;
            int age = Integer.parseInt(ageStr);

            User user = new User();
            user.setName(name);
            user.setAge(age);

            userService.userRegister(user);
            JOptionPane.showMessageDialog(null, "Successfully registered user");
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid age, must be a number");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "User registration error: " + e.getMessage());
        }
    }
}
