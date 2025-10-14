package service;

import dao.DBConnection;
import dao.PartnerDao;
import dao.PartnerDaoJDBC;
import errors.BadRequestException;
import errors.ConflictException;
import errors.NotFoundException;
import model.Partner;
import util.ValidatorUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PartnerService {

    private final PartnerDao partnerDao;

    // Pasamos la conexion
    public PartnerService() {
        try {
            Connection connection = DBConnection.getConnection();
            this.partnerDao = new PartnerDaoJDBC(connection);
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to database: " + e.getMessage());
        }
    }

    // Crear un socio
    public void createPartner(Partner partner) throws SQLException, BadRequestException, ConflictException {
        // Validaciones básicas
        ValidatorUtil.validateNotNull(partner, "The partner cannot be null.");
        ValidatorUtil.validateNotNull(partner.getName(), "The partner name cannot be null.");
        ValidatorUtil.validateNotNull(partner.getEmail(), "The partner email cannot be null.");

        if (partner.getName().trim().isEmpty()) {
            throw new BadRequestException("The partner name cannot be empty.");
        }

        if (partner.getEmail().trim().isEmpty()) {
            throw new BadRequestException("The partner email cannot be empty.");
        }

        // Validar formato del email
        if (!ValidatorUtil.isValidEmail(partner.getEmail())) {
            throw new BadRequestException("The email format is invalid.");
        }

        // Verificar duplicado
        if (partnerExists(partner.getEmail())) {
            throw new ConflictException("A partner with this email already exists.");
        }

        // Crear el socio
        partnerDao.create(partner);
    }

    // Verificar si un socio existe por su email
    private boolean partnerExists(String email) throws SQLException {
        List<Partner> partners = partnerDao.getAll();
        for (Partner partner : partners) {
            if (partner.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // Obtener un socio por ID
    public Partner getPartnerById(int id) throws SQLException, BadRequestException {
        if (!ValidatorUtil.isPositiveNumber(id)) {
            throw new BadRequestException("The partner ID must be a positive number.");
        }

        Partner partner = partnerDao.getById(id);
        if (partner == null) {
            throw new BadRequestException("Partner not found.");
        }
        return partner;
    }

    // Obtener todos los socios
    public List<Partner> getAllPartners() throws SQLException {
        return partnerDao.getAll();
    }

    // 🔹 Actualizar socio
    public void updatePartner(Partner partner) throws SQLException, BadRequestException, NotFoundException {
        ValidatorUtil.validateNotNull(partner, "The partner cannot be null.");
        ValidatorUtil.validateNotNull(partner.getName(), "The partner name cannot be null.");
        ValidatorUtil.validateNotNull(partner.getEmail(), "The partner email cannot be null.");

        if (!ValidatorUtil.isPositiveNumber(partner.getId())) {
            throw new BadRequestException("The partner ID must be a positive number.");
        }

        Partner existing = partnerDao.getById(partner.getId());
        if (existing == null) {
            throw new NotFoundException("Partner not found with ID " + partner.getId());
        }

        partnerDao.update(partner);
    }

    // 🔹 Eliminar socio
    public void deletePartner(int id) throws SQLException, NotFoundException {
        Partner existing = partnerDao.getById(id);
        if (existing == null) {
            throw new NotFoundException("Partner not found with ID " + id);
        }
        partnerDao.delete(id);
    }
}
