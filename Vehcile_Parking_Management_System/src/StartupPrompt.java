import ui.LoginForm;
import ui.RegisterForm;

import javax.swing.*;

public class StartupPrompt {
    public static void show() {
        String[] options = {"Register", "Login"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome! Are you a new user or existing user?",
                "User Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            new RegisterForm(); // New User
        } else if (choice == 1) {
            new LoginForm(); // Existing User
        } else {
            System.exit(0); // If user closes the dialog
        }
    }
}
