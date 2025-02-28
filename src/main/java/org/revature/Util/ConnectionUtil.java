package org.revature.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String username = "postgres";
    private static String password = "Minombre01";

    private static Connection connection = null;


    public static Connection getConnection(){
        if (connection == null){
            try {
                connection = DriverManager.getConnection(url, username, password);

            } catch (SQLException e){
                e.printStackTrace();;
            }
        }

        return connection;
    }
}
