package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String PROPERTIES_FILE = "/db.properties";

    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException{
        Properties props = new Properties();
        try(InputStream is = DBConnection.class.getResourceAsStream(PROPERTIES_FILE)){
            if (is == null){
                throw new IOException("It was not found " + PROPERTIES_FILE + " in resources");
            }
            props.load(is);
        }

        String driver = props.getProperty("db.driver");
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        if (driver != null && !driver.trim().isEmpty()){
            Class.forName(driver);
        }
        return DriverManager.getConnection(url,user,pass);
    }
}
