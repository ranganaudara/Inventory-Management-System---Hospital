package db_utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String SQCONN =    "jdbc:sqlite:inventory.db";
    private static Connection c = null;
    public static Connection getConnection() throws SQLException {
        try{
            if(c == null) {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection(SQCONN);
                return c;
            }
            return c;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
