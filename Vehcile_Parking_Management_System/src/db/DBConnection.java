package db;
//This file is like your "power plug" to MySQL — it provides a reusable method to connect to your database whenever needed.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/parking_system"; //This is the JDBC URL — it tells Java where your database is.
//    localhost: your own computer
//    3306: default port for MySQL
//    parking_system: your database name
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = "";  // Replace with your MySQL password-It is blank only.

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // For MySQL 8+. Class.forName(...) dynamically loads the MySQL JDBC driver class.It’s like: “Hey Java, I’ll be using MySQL — load the required code.”This line is optional in newer Java versions but still safe to include.
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);  //Create a live pipe between Java and your MySQL database using these credentials.
            System.out.println("✅ Connected to MySQL!");
            return conn; //We return the active connection object so that other classes (like UserDAO) can use it to execute SQL queries.
        } catch (ClassNotFoundException | SQLException e) {  //If something fails (wrong password, DB not running, driver missing), it catches the error and avoids crashing the app.
            System.out.println("❌ DB Connection Error: " + e.getMessage());
            return null;
        }
    }
}

/*
com.mysql.cj.jdbc.Driver — What Is It?
This is the MySQL JDBC Driver class.

com.mysql → MySQL package
cj → stands for Connector/J (which is MySQL's official JDBC driver)
jdbc.Driver → the class that implements the JDBC API to talk to MySQL from Java

📦 Where does it come from?
It comes from the MySQL Connector/J library — a .jar file that we:
added manually to our project-->Right-click your project → Open Module Settings
Go to Libraries → click +
Select Java → Choose the .jar file
Click Apply and OK
 */
