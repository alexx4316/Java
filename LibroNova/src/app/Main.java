package app;

import dao.DBConnection;
import view.MenuView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection dbConnection = null;
        dbConnection = DBConnection.getConnection();
        new MenuView(dbConnection).showMenu();
    }
}

