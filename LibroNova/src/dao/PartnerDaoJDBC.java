package dao;

import model.Partner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartnerDaoJDBC implements PartnerDao{

    private final String SQL_CREATE = "INSERT INTO partners (name, email, role, status, register_date) VALUES (?,?,?,?,?)";
    private final String SQL_UPDATE = "UPDATE partners set name = ?, email = ?, rol = ?, status = ?, register_date = ? WHERE id = ?";
    private final String SQL_GET_BY_ID = "SELECT * FROM partners WHERE id = ?";
    private final String SQL_GET_ALL = "SELECT * FROM partners";
    private final String SQL_DELETE = "DELETE FROM partners WHERE id = ?";

    private Connection connection;
    public PartnerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void create(Partner partner) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE)){
            ps.setString(1, partner.getName());
            ps.setString(2, partner.getEmail());
            ps.setString(3, partner.getRole());
            ps.setBoolean(4, partner.isStatus());
            ps.setDate(5, new java.sql.Date(partner.getRegister_date().getTime()));
            ps.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("Error creating partner", e);
        }
    }

    @Override
    public Partner getById(int id) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_ID)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Partner(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getBoolean("status"),
                        rs.getDate("register_date")
                );
            }
        } catch (SQLException e){
            throw new SQLException("Error finding partner");
        }
        return null;
    }

    @Override
    public void update(Partner partner) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)){
            ps.setString(1, partner.getName());
            ps.setString(2, partner.getEmail());
            ps.setString(3, partner.getRole());
            ps.setBoolean(4, partner.isStatus());
            ps.setDate(5, new java.sql.Date(partner.getRegister_date().getTime()));
            ps.setInt(6, partner.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("Error updating partner", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(SQL_DELETE)){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e){
            throw new SQLException("Error deleting partner", e);
        }
    }

    @Override
    public List<Partner> getAll() throws SQLException {
        List<Partner> partners = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                partners.add(new Partner(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getBoolean("status"),
                        rs.getDate("register_date")
                ));
            }
        } catch (SQLException e){
            throw new SQLException("Error listing partners", e);
        }
        return partners;
    }
}
