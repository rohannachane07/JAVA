package ui;

import model.*;
import service.ParkingLot;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkingApp extends JFrame {
    private ParkingLot lot;
    private DefaultTableModel tableModel;

    public ParkingApp(String username) {
        lot = new ParkingLot();
        setTitle("Vehicle Parking System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Top Panel (Header + Entry form stacked) ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Header: Welcome message + Logout button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("  Logged in as: " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JButton logoutButton = new JButton("Logout");

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Entry form
        JPanel entryPanel = new JPanel(new FlowLayout());
        JTextField numberField = new JTextField(10);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Car", "Bike", "Truck"});
        JButton addButton = new JButton("Park Vehicle");

        entryPanel.add(new JLabel("Vehicle Number:"));
        entryPanel.add(numberField);
        entryPanel.add(new JLabel("Type:"));
        entryPanel.add(typeBox);
        entryPanel.add(addButton);

        // Stack headerPanel and entryPanel vertically
        JPanel stackedTop = new JPanel();
        stackedTop.setLayout(new GridLayout(2, 1));
        stackedTop.add(headerPanel);
        stackedTop.add(entryPanel);

        add(stackedTop, BorderLayout.NORTH);  // Place at the top of the frame

        // === Vehicle Table Center ===
        tableModel = new DefaultTableModel(new String[]{"Number", "Type", "Entry Time"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        // === Bottom Panel: Remove + Print ===
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JTextField removeField = new JTextField(10);
        JButton removeButton = new JButton("Remove Vehicle");
        JButton printBillButton = new JButton("Print Bill");
        JLabel resultLabel = new JLabel(" ");

        bottomPanel.add(new JLabel("Vehicle Number:"));
        bottomPanel.add(removeField);
        bottomPanel.add(removeButton);
        bottomPanel.add(printBillButton);
        bottomPanel.add(resultLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        // === Action Listeners ===

        // Logout logic
        logoutButton.addActionListener(e -> {
            dispose();  // Close current window
            new LoginForm();  // Show login form again
        });

        // Park Vehicle logic
        addButton.addActionListener((ActionEvent e) -> {
            String number = numberField.getText().trim();
            String type = (String) typeBox.getSelectedItem();

            if (number.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter vehicle number!");
                return;
            }

            Vehicle vehicle = switch (type) {
                case "Car" -> new Car(number);
                case "Bike" -> new Bike(number);
                case "Truck" -> new Truck(number);
                default -> null;
            };

            if (lot.parkVehicle(vehicle)) {
                tableModel.addRow(new Object[]{
                        vehicle.getNumber(),
                        vehicle.getType(),
                        formatTimestamp(vehicle.getEntryTime())
                });
                numberField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle already parked or invalid.");
            }
        });

        // Remove logic
        removeButton.addActionListener((ActionEvent e) -> {
            String number = removeField.getText().trim();
            Vehicle removed = lot.removeVehicle(number);

            if (removed != null) {
                resultLabel.setText("Charges: ₹" + String.format("%.2f", removed.calculateCharge()));
                removeField.setText("");

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(number)) {
                        tableModel.removeRow(i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle not found.");
            }
        });

        // Print bill logic
        printBillButton.addActionListener((ActionEvent e) -> {
            String number = removeField.getText().trim();
            Vehicle vehicle = lot.getVehicle(number);

            if (vehicle != null) {
                long now = System.currentTimeMillis();
                long duration = now - vehicle.getEntryTime();
                long minutes = duration / (60 * 1000);
                long hours = minutes / 60;
                minutes = minutes % 60;

                double charges = vehicle.calculateCharge();

                String bill = """
                        === Parking Bill ===
                        Vehicle Number: %s
                        Type: %s
                        Entry Time: %s
                        Exit Time: %s
                        Time Spent: %d hours %d minutes
                        Charges: ₹%.2f
                        """.formatted(
                        vehicle.getNumber(),
                        vehicle.getType(),
                        formatTimestamp(vehicle.getEntryTime()),
                        formatTimestamp(now),
                        hours, minutes,
                        charges
                );

                JOptionPane.showMessageDialog(this, bill);
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle not found.");
            }
        });

        setVisible(true);
    }

    private String formatTimestamp(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        return sdf.format(new Date(millis));
    }
}
