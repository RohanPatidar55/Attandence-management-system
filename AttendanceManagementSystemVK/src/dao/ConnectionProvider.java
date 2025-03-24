package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionProvider {

    private static final String DB_NAME = "attendancedb";
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Initial connection to MySQL without specifying a database
            try (Connection tempCon = DriverManager.getConnection(DB_URL + "?useSSL=false&allowPublicKeyRetrieval=true", DB_USERNAME, DB_PASSWORD);
                 Statement stmt = tempCon.createStatement()) {

                if (!dataBaseExists(tempCon, DB_NAME)) {
                    createDataBase(tempCon, DB_NAME);
                }
            }

            // Now connect to the actual database
            return DriverManager.getConnection(DB_URL + "/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true", DB_USERNAME, DB_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean dataBaseExists(Connection con, String dbName) throws SQLException {
        String query = "SHOW DATABASES LIKE '" + dbName + "'";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next();
        }
    }

    private static void createDataBase(Connection con, String dbName) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE " + dbName);
            System.out.println("✅ Database '" + dbName + "' created successfully!");
        }
    }
}
