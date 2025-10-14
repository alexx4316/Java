package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "resources/db.properties";
    private static final String HOST;
    private static final String PORT;
    private static final String USER;
    private static final String DATABASE;
    private static final String PASSWORD;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("File not found" + PROPERTIES_FILE);
            }

            Properties props = new Properties();
            props.load(input);

            HOST = props.getProperty("db.host");
            PORT = props.getProperty("db.port");
            USER = props.getProperty("db.user");
            DATABASE = props.getProperty("db.database");
            PASSWORD = props.getProperty("db.password");

            if (HOST == null || PORT == null || USER == null || DATABASE == null || PASSWORD == null) {
                throw new RuntimeException("Missing properties in the db.properties file");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading database configuration: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;
        Connection connection = DriverManager.getConnection(url, USER, PASSWORD);
        System.out.println("Successfully connected to Supabase PostgreSQL");
        return connection;
    }
}
