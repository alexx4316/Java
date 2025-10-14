package dao;


import model.Partner;

import java.sql.SQLException;
import java.util.List;

public interface PartnerDao {
    void create(Partner partner) throws SQLException;
    Partner getById(int id) throws SQLException;
    void update(Partner partner) throws SQLException;
    void delete(int id) throws SQLException;
    List<Partner> getAll() throws SQLException;
}
