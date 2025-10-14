package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = "aws-1-us-east-2.pooler.supabase.com";
    private static  final String PORT = "6543";
    private static final String USER = "postgres.oliljyxrnnpaumqsztsb";
    private static final String DATABASE = "postgres";
    private static final String PASSWORD = "Andres2502*";

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
