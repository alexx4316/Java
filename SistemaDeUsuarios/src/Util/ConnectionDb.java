package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {
    private static final String URL = "jdbc:mysql://localhost:3306/controlUsuarios";
    private static final String USER = "andres";
    private static final String PASSWORD = "Andres2502*";

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            throw new RuntimeException("Error", e);
        }
    }
}
