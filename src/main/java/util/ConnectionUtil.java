package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection con = null;
    private static final String url = "jdbc:mysql://localhost:3306/PasswordManager";
    private static final String username = "manager";
    private static final String password = "Manager@123";

    public static Connection connectToDB() {
        try {
            con = DriverManager.getConnection(url,username,password);
            return con;
        }catch(SQLException e) {
            e.printStackTrace();
            return con;
        }
    }
}
