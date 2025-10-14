import dao.UserDAO;
import dao.UserDAOImpl;
import menu.MenuConsole;
import service.UserService;
import util.DBConnection;

import java.sql.Connection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try(Connection connection = DBConnection.getConnection()){
            UserDAO userDAO = new UserDAOImpl(connection);
            UserService userService = new UserService(userDAO, connection);

            MenuConsole menu = new MenuConsole(userService);
            menu.show();
        } catch (Exception e) {
            System.err.println("Fatal error" + e.getMessage());
        }
    }
}