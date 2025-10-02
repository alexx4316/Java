package Model;

import Dao.UserDao;

import javax.swing.*;

public class Admin extends User{

    public Admin(String name, String email, String password) {
        super(name, email, password, "ADMIN");
    }

    @Override
    public void viewProfile() {
        String info = "=== ADMIN PROFILE ===\n" +
                "Name: " + getName() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Role: " + getRol() + "\n" +
                "Status: " + (isActive() ? "Active" : "Locked");
        JOptionPane.showMessageDialog(null, info, "Perfil", JOptionPane.INFORMATION_MESSAGE);
    }

    public void blockUser(User user){
        if (user == this){
            JOptionPane.showMessageDialog(null,"An admin cannot block itself");
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                null,
                "Surely you want to block" + user.getName() + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION){
            user.setActivo(false);
            new UserDao().updateStatus(user.getEmail(), false);
            JOptionPane.showMessageDialog(null, "The user " + user.getName() + " It has been blocked");
        } else {
            JOptionPane.showMessageDialog(null, "Canceled");
        }

    }
    public void unlockUser(User user){

        int option = JOptionPane.showConfirmDialog(
                null,
                "Surely you want to unblock" + user.getName() + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION){
            user.setActivo(true);
            new UserDao().updateStatus(user.getEmail(), true);
            JOptionPane.showMessageDialog(null,"The user " + user.getName() + " It has been unblocked ");
        } else {
            JOptionPane.showMessageDialog(null, "Canceled");
        }
    }
}
