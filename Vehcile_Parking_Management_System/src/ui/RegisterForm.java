package ui;

import model.User;
import service.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm extends JFrame {
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterForm() {
        setTitle("User Registration");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        add(new JLabel()); // empty cell
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User(name, email, username, password);
        UserDAO dao = new UserDAO();
        boolean success = dao.registerUser(user);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // close register window
            new LoginForm(); // open login
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Entry point of the app
    public static void main(String[] args) {
        new RegisterForm(); // Open register form first
    }
}
