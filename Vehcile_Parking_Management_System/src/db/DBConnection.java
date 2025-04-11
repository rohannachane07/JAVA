package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/parking_system";
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = "";  // Replace with your MySQL password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // For MySQL 8+
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL!");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ DB Connection Error: " + e.getMessage());
            return null;
        }
    }
}
