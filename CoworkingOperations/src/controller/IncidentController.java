package controller;

import errors.ServiceException;
import service.IncidentService;

import javax.swing.*;

public class IncidentController {
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService){
        this.incidentService = incidentService;
    }

    public void viewIncident() {
        try {
            String incidents = incidentService.getAllIncidents().toString();
            JOptionPane.showMessageDialog(null,
                    incidents.isEmpty() ? "No incidents found." : incidents,
                    "Incident List",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while loading incidents: " + e.getMessage(),
                    "Service Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Unexpected error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void manageMaintenance() {
        try {
            String incidentId = JOptionPane.showInputDialog(null, "Enter incident ID to mark as resolved:");
            if (incidentId == null || incidentId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operation cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            incidentService.resolveIncident(Integer.parseInt(incidentId));
            JOptionPane.showMessageDialog(null,
                    "Incident marked as resolved successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid ID format. Please enter a valid number.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null,
                    "Error resolving incident: " + e.getMessage(),
                    "Service Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Unexpected error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
