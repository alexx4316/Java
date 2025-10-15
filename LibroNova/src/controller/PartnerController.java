package controller;

import errors.BadRequestException;
import errors.ConflictException;
import errors.NotFoundException;
import model.Partner;
import service.PartnerService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class PartnerController {
    private final PartnerService partnerService = new PartnerService();

    // Crear socio
    public void createPartner(Partner partner) {
        try {
            partnerService.createPartner(partner);
            JOptionPane.showMessageDialog(null, "Partner created successfully.");
        } catch (BadRequestException e) {
            JOptionPane.showMessageDialog(null, "Bad Request: " + e.getMessage());
        } catch (ConflictException e) {
            JOptionPane.showMessageDialog(null, "Conflict: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    // Listar todos los socios
    public void listPartners() {
        try {
            List<Partner> partners = partnerService.getAllPartners();
            if (partners.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No partners registered.");
                return;
            }

            StringBuilder sb = new StringBuilder("=== List of Partners ===\n");
            for (Partner p : partners) {
                sb.append("ID: ").append(p.getId())
                        .append(" | Name: ").append(p.getName())
                        .append(" | Email: ").append(p.getEmail())
                        .append(" | Role: ").append(p.getRole())
                        .append(" | Status: ").append(p.isStatus())
                        .append(" | Register date: ").append(p.getRegister_date())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving partners: " + e.getMessage());
        }
    }

    // Obtener socio por ID
    public void getPartnerById(int id) {
        try {
            Partner partner = partnerService.getPartnerById(id);
            if (partner == null) {
                throw new NotFoundException("Partner not found with ID " + id);
            }
            JOptionPane.showMessageDialog(null, "Partner found:\n" + partner);
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    // Actualizar socio
    public void updatePartner(Partner partner) {
        try {
            partnerService.updatePartner(partner);
            JOptionPane.showMessageDialog(null, "Partner updated successfully.");
        } catch (BadRequestException e) {
            JOptionPane.showMessageDialog(null, "Invalid data: " + e.getMessage());
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, "Partner not found: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    // Eliminar socio
    public void deletePartner(int id) {
        try {
            partnerService.deletePartner(id);
            JOptionPane.showMessageDialog(null, "Partner deleted successfully.");
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, "Partner not found: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }
}
