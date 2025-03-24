package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Tables {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;

        try {
            con = ConnectionProvider.getCon();
            st = con.createStatement();

            // ✅ Creating userdetails table
            if (!tableExists(st, "userdetails")) {
                String sql = "CREATE TABLE userdetails ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(255) NOT NULL, "
                        + "gender VARCHAR(50) NOT NULL, "
                        + "email VARCHAR(255) NOT NULL, "
                        + "contact VARCHAR(20) NOT NULL, "
                        + "address VARCHAR(255), "
                        + "state VARCHAR(100), "
                        + "country VARCHAR(100), "
                        + "uniqueregid VARCHAR(100) NOT NULL, "
                        + "imagename VARCHAR(100)"
                        + ")";
                st.executeUpdate(sql);
                System.out.println("✅ Table 'userdetails' created successfully!");
            }

            // ✅ Creating userattendance table
            if (!tableExists(st, "userattendance")) {
                String sql = "CREATE TABLE userattendance ("
                        + "userid INT AUTO_INCREMENT PRIMARY KEY, "
                        + "date DATE NOT NULL, "
                        + "checkin DATETIME, "
                        + "checkout DATETIME, "
                        + "workduration VARCHAR(100)"
                        + ")";
                st.executeUpdate(sql);
                System.out.println("✅ Table 'userattendance' created successfully!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean tableExists(Statement st, String tableName) throws SQLException {
        try (ResultSet resultSet = st.executeQuery("SHOW TABLES LIKE '" + tableName + "'")) {
            return resultSet.next();
        }
    }
}
