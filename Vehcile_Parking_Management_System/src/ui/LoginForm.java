package ui;

import service.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel welcomeLabel;
    private JButton logoutButton;

    public LoginForm() {
        setTitle("User Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(new JLabel()); // Empty cell
        add(loginButton);

        welcomeLabel = new JLabel("");
        add(welcomeLabel);

        logoutButton = new JButton("Logout");
        logoutButton.setVisible(false);
        add(logoutButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        logoutButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            welcomeLabel.setText("");
            logoutButton.setVisible(false);
            loginButton.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Logged out successfully.");
        });

        setVisible(true);
    }

    private void authenticateUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        UserDAO dao = new UserDAO();
        if (dao.loginUser(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
            loginButton.setEnabled(false);
            logoutButton.setVisible(true);
            welcomeLabel.setText("Logged in as: " + username);
            dispose(); // Close login window

            // Launch console-based parking system
             new ParkingApp(username); // This should open a visible GUI window

        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
