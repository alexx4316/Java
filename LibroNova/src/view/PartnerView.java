package view;

import controller.PartnerController;
import model.Partner;

import javax.swing.*;
import java.util.Date;

public class PartnerView {
    private final PartnerController controller;

    public PartnerView(PartnerController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        String opcion;
        do {
            opcion = JOptionPane.showInputDialog("""
                === Partner Management ===
                1. List partners
                2. Register partner
                3. Edit partner
                4. Delete partner
                0. Back
            """);

            if (opcion == null) return;

            switch (opcion) {
                case "1" -> controller.listPartners();

                case "2" -> {
                    try {
                        String name = JOptionPane.showInputDialog("Enter Partner Name:");
                        String email = JOptionPane.showInputDialog("Enter Partner Email:");
                        String role = JOptionPane.showInputDialog("Enter Partner Role:");
                        String statusStr = JOptionPane.showInputDialog("Enter Partner Status (True/False):");
                        String dateStr = JOptionPane.showInputDialog("Enter Partner register date:");

                        boolean status = Boolean.parseBoolean(statusStr);
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        Date register_date = sdf.parse(dateStr);

                        Partner partner = new Partner(name, email, role, status, register_date);
                        controller.createPartner(partner);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                    }
                }

                case "3" -> {
                    try {
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Partner ID to edit:"));
                        String name = JOptionPane.showInputDialog("Enter Partner Name:");
                        String email = JOptionPane.showInputDialog("Enter Partner Email:");
                        String role = JOptionPane.showInputDialog("Enter Partner Role:");
                        String statusStr= JOptionPane.showInputDialog("Enter Partner Status (True/False):");
                        String dateStr = JOptionPane.showInputDialog("Enter Partner register date:");

                        boolean status = Boolean.parseBoolean(statusStr);
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        Date register_date = sdf.parse(dateStr);

                        Partner partner = new Partner(id, name, email, role, status, register_date);
                        controller.updatePartner(partner);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                    }
                }

                case "4" -> {
                    try {
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Partner ID to delete:"));
                        controller.deletePartner(id);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "ID must be a number.");
                    }
                }

                case "0" -> {}

                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        } while (!"0".equals(opcion));
    }
}
