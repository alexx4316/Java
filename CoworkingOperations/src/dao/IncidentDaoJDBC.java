package dao;

import domain.Incident;
import errors.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncidentDaoJDBC implements IncidentDao{

    private final String SQL_INSERT = "INSERT INTO incidents (description, reported_by, reported_at, status) VALUES (?, ?, ?, ?)";
    private final String SQL_LIST_ALL = "SELECT * FROM incidents";
    private final String SQL_FIND_BY_ID = "SELECT * FROM incidents WHERE id = ?";
    private final String SQL_UPDATE_STATUS = "UPDATE incidents SET status = ? WHERE id = ?";
    private final String SQL_DELETE = "DELETE FROM incidents WHERE id = ?";

    private Connection connection;
    public IncidentDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Incident incident) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_INSERT)){
            ps.setString(1, incident.getDescription());
            ps.setString(2, incident.getReportedBy());
            ps.setTimestamp(3, Timestamp.valueOf(incident.getReportedAt()));
            ps.setString(4, incident.getStatus());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error creating incident", e);
        }
    }

    @Override
    public List<Incident> findAll() throws DataAccessException {
        List<Incident> incidents = new ArrayList<>();
        try(Statement ps = connection.createStatement();
            ResultSet rs = ps.executeQuery(SQL_LIST_ALL)){
            while (rs.next()){
                Incident incident = new Incident(
                        rs.getInt("id"),
                        rs.getString("reported_by"),
                        rs.getTimestamp("reported_at").toLocalDateTime(),
                        rs.getString("status")
                );
                incidents.add(incident);
            }
        } catch (SQLException e){
            throw new DataAccessException("Error retrieving incidents", e);
        }
        return incidents;
    }

    @Override
    public Incident findById(int id) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Incident(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("reported_by"),
                        rs.getTimestamp("reported_at").toLocalDateTime(),
                        rs.getString("status")
                );
            }
        } catch (SQLException e){
            throw new DataAccessException("Error finding incidents", e);
        }
        return null;
    }

    @Override
    public void updateStatus(int id, String newStatus) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_STATUS)){
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error updating status", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_DELETE)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error deleting incident", e);
        }
    }
}
