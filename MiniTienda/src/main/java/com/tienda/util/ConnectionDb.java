package main.java.com.tienda.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {

    private static final String URL = "jdbc:mysql://localhost:3306/miniTienda";
    private static final String USER = "andres";
    private static final String PASSWORD = "Andres2502*";

    // Obtenemos las conexion
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            System.err.println("Error al conectar a la bd" + e.getMessage());
            throw new RuntimeException("Error al conectar a la bd" + e);
        }
    }

    // Cerrar la conexion
    public static void CloseConnection(Connection conn){
        if (conn == null){
            try{
                conn.close();
                System.out.println("Conexion cerrada con exito");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexion" + e.getMessage());
            }
        }
    }
}
