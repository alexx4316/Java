package dao;

import domain.Incident;
import errors.DataAccessException;

import java.util.List;

public interface IncidentDao {
        void create(Incident incident) throws DataAccessException;
        List<Incident> findAll() throws DataAccessException;
        Incident findById(int id) throws DataAccessException;
        void updateStatus(int id, String newStatus) throws DataAccessException;
        void delete(int id) throws DataAccessException;
}
