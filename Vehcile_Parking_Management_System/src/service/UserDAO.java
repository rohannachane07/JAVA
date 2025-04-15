package service;
//Purpose: Responsible for performing database operations (register, login) for the User model.
//        DAO = Data Access Object
//        It separates business logic from data access logic.
import model.User;
import db.DBConnection;

import java.sql.*;

public class UserDAO {

    // Register a new user
    public boolean registerUser(User user) {    //This method inserts a new user into the database.

        String sql = "INSERT INTO users(name, email, username, password) VALUES (?, ?, ?, ?)"; // Uses placeholders ? to safely insert user data (avoids SQL injection).

        try (Connection conn = DBConnection.getConnection();  //Gets connection from your custom DBConnection class
             PreparedStatement stmt = conn.prepareStatement(sql)) //Prepares a statement using the SQL query
        {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());

            int rowsInserted = stmt.executeUpdate();  //Executes the insert query
            return rowsInserted > 0; //If 1+ row inserted → return true = success

        } catch (SQLException e)
        {  // If anything goes wrong (e.g., DB down, duplicate user), print message and return false.
            System.out.println("Register Error: " + e.getMessage());
            return false;
        }
    }

    // Login check
    public boolean loginUser(String username, String password)  //This method checks whether a user with the given username and password exists in the database.
    {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();  //rs refers to a ResultSet — it's like a table in memory that holds the data retrieved from the database after running a SQL query.
            // rs.next() means:"Move the cursor to the next row in the result."The very first time you call rs.next(), it moves the cursor to the first row of the result.
            return rs.next(); // If ResultSet has a row → user exists → return true.

        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
            return false;
        }
    }

}

/*

? is the standard JDBC placeholder symbol.
It tells the database:
“Insert a parameter here safely — don’t interpret it as part of the SQL.”
Behind the scenes, JDBC doesn’t just do string replacement. It uses a binary-safe method to send values to the database engine. That’s why it's super secure.

These placeholders are safe substitutions. You later set the values like this:
stmt.setString(1, user.getName());
stmt.setString(2, user.getEmail());

What is SQL Injection?
SQL Injection is a security vulnerability that happens when untrusted input is directly embedded into SQL queries.

String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
If someone enters this as a username:
' OR '1'='1
Then the query becomes:

SELECT * FROM users WHERE username = '' OR '1'='1' AND password = ''
This will bypass login, because '1'='1' is always true! 😱
That’s a classic SQL Injection attack.

 How ? Protects You
When you use:

PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
stmt.setString(1, username);
stmt.setString(2, password);
No matter what crazy thing the user types (like ' OR '1'='1), it's treated as a value, not code.

So the query safely becomes something like:

SELECT * FROM users WHERE username = "' OR '1'='1" AND password = ""
➡ No match. No attack. App stays safe. 🔐

 */
