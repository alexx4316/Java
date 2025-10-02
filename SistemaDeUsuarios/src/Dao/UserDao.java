package Dao;

import Util.ConnectionDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Client;
import Model.Admin;
import Model.User;



public class UserDao{

    private int findRoleIdByName(Connection conn, String roleName) throws SQLException {
        String sql = "SELECT id FROM roles WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleName == null ? null : roleName.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
                throw new SQLException("Role not found: " + roleName);
            }
        }
    }

    // Añadir un nuevo usuario
    public void addUser(User user) throws SQLException {
        String insertUserSql = "INSERT INTO users (name, email, password, role_id, status) VALUES (?, ?, ?, ?, ?)";
        String insertClientSql = "INSERT INTO clients (user_id, balance, address, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionDb.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psUser = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, user.getName());
                psUser.setString(2, user.getEmail());
                psUser.setString(3, user.getPassword());

                int roleId = findRoleIdByName(conn, user.getRol());
                psUser.setInt(4, roleId);

                psUser.setString(5, "ACTIVE");

                psUser.executeUpdate();

                try (ResultSet keys = psUser.getGeneratedKeys()) {
                    if (keys.next()) {
                        int userId = keys.getInt(1);

                        if (user instanceof Client) {
                            Client c = (Client) user;
                            try (PreparedStatement psClient = conn.prepareStatement(insertClientSql)) {
                                psClient.setInt(1, userId);
                                // si balance puede ser null, maneja con setObject o BigDecimal
                                psClient.setDouble(2, c.getBalance()); // o psClient.setBigDecimal(2, c.getBalanceBigDecimal())
                                psClient.setString(3, c.getAddress());
                                psClient.setString(4, c.getPhone());
                                psClient.executeUpdate();
                            }
                        }
                    } else {
                        throw new SQLException("No se obtuvo id generado para user");
                    }
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }



    // Encontrar usuario por su emal
    public User findByEmail(String email) {
        String sql = """
        SELECT u.id,
               u.name,
               u.email,
               u.password,
               u.role_id,
               r.name AS role_name,
               u.status,
               c.balance AS balance,
               c.address AS address,
               c.phone AS phone
        FROM users u
        LEFT JOIN roles r ON u.role_id = r.id
        LEFT JOIN clients c ON c.user_id = u.id
        WHERE u.email = ?
        """;

        try (Connection connection = ConnectionDb.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email == null ? null : email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String roleName = rs.getString("role_name");
                String name = rs.getString("name");
                String mail = rs.getString("email");
                String password = rs.getString("password");

                if ("ADMIN".equalsIgnoreCase(roleName)) {
                    return new Admin(name, mail, password);
                } else {
                    Double balance = null;
                    double bal = rs.getDouble("balance");
                    if (!rs.wasNull()) balance = bal;

                    String roleId = rs.getString("role_id");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");

                    return new Client(name, mail, password, roleId, balance == null ? 0.0 : balance, address, phone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        String sql = """
        SELECT u.id,
               u.name,
               u.email,
               u.password,
               u.role_id,
               r.name AS role_name,
               u.status,
               c.balance AS balance,
               c.address AS address,
               c.phone AS phone
        FROM users u
        LEFT JOIN roles r ON u.role_id = r.id
        LEFT JOIN clients c ON c.user_id = u.id
        """;

        List<User> users = new ArrayList<>();
        try (Connection conn = ConnectionDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String roleName = rs.getString("role_name");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String statusStr = rs.getString("status");
                boolean isActive = statusToBoolean(statusStr);

                if ("ADMIN".equalsIgnoreCase(roleName)) {
                    Admin admin = new Admin(name, email, password);
                    admin.setStatus(isActive);
                    users.add(admin);
                } else {
                    // balance con control de NULL
                    Double balance = null;
                    double b = rs.getDouble("balance");
                    if (!rs.wasNull()) balance = b;

                    String roleId = rs.getString("role_id");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");

                    Client client = new Client(
                            name,
                            email,
                            password,
                            roleId,
                            balance == null ? 0.0 : balance,
                            address,
                            phone
                    );
                    client.setStatus(isActive);
                    users.add(client);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }



    // Actualizar su status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_BLOCKED = "BLOCKED";

    public boolean updateStatus(String email, Boolean active) {
        String sql = "UPDATE users SET status = ? WHERE email = ?";
        if (email == null || email.trim().isEmpty()) return false;

        String statusText = (active != null && active) ? STATUS_ACTIVE : STATUS_BLOCKED;
        try (Connection connection = ConnectionDb.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, statusText);
            ps.setString(2, email.trim());
            int rows = ps.executeUpdate();
            System.out.println("updateStatus: email=" + email + " status=" + statusText + " rowsAffected=" + rows);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean statusToBoolean(String status) {
        if (status == null) return true;
        return "active".equalsIgnoreCase(status) || "ACTIVE".equals(status);
    }

}
