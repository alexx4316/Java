package service;

import dao.IncidentDao;
import domain.Incident;
import errors.DataAccessException;

import java.time.LocalDateTime;
import java.util.List;

public class IncidentService {
    private final IncidentDao incidentDao;

    public IncidentService(IncidentDao incidentDao){
        this.incidentDao = incidentDao;
    }

    public void reportIncident(String description, String reportedBy) throws DataAccessException {
        Incident incident = new Incident(description,reportedBy, LocalDateTime.now(), "OPEN");
        incidentDao.create(incident);
    }

    public List<Incident> getAllIncidents() throws DataAccessException{
        return incidentDao.findAll();
    }

    public void resolveIncident(int id) throws DataAccessException{
        incidentDao.updateStatus(id, "RESOLVED");
    }

    public void deleteIncident(int id) throws DataAccessException{
        incidentDao.delete(id);
    }
}
