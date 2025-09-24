package Model;

import javax.swing.*;

public class Client extends User {
    private String address;
    private String phone;
    private double balance;

    // Constructor
    public Client(String name, String email, String password, String rol, double balance, String address, String phone) {
        super(name, email, password, rol);
        this.balance = balance;
        this.address = address;
        this.phone = phone;
    }

    // Setters y Getters
    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance){
        if (balance >= 0){
            this.balance = balance;
        } else {
        JOptionPane.showMessageDialog(null, "The balance cannot be negative");
        }
    }

    public void makePurchase(double amount){
        if (!isActive()){
            JOptionPane.showMessageDialog(null, "The customer is blocked and cannot make purchases");
            return;
        }

        if (amount <= balance){
            balance -= amount;
            JOptionPane.showMessageDialog(null, getName() + "you have made a purchase of $: " + amount);
        } else {
            JOptionPane.showMessageDialog(null, "insufficient balance");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()){
            this.address = address;
        } else {
            JOptionPane.showMessageDialog(null, "The address cannot be empty");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null && phone.matches("\\+?\\d{7,15}")){
            this.phone = phone;
        } else {
            JOptionPane.showMessageDialog(null, "Phone must have between 7 and 15 digits (optional + at start).");
        }
    }

    @Override
    public void viewProfile() {
        JOptionPane.showMessageDialog(null,
                "Client: " + getName() +
                "\nEmail: " + getEmail() +
                "\nBalance: " + getBalance() +
                "\nStatus: " + (isActive() ? "Si" : "No") +
                "\nAddress: " + getAddress() +
                "\nPhone: " + getPhone()
        );
    }
}
