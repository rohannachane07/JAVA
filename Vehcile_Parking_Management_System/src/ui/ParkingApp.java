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

    public ParkingApp() {
        lot = new ParkingLot();
        setTitle("Vehicle Parking System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Top Panel: Add Vehicle ===
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JTextField numberField = new JTextField(10);
        String[] types = {"Car", "Bike", "Truck"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        JButton addButton = new JButton("Park Vehicle");

        topPanel.add(new JLabel("Vehicle Number:"));
        topPanel.add(numberField);
        topPanel.add(new JLabel("Type:"));
        topPanel.add(typeBox);
        topPanel.add(addButton);

        // === Center: Vehicle Table ===
        String[] columnNames = {"Number", "Type", "Entry Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // === Bottom Panel: Remove Vehicle + Print Bill ===
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

        // === Add all panels ===
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // === Action Listeners ===

        addButton.addActionListener((ActionEvent e) -> {
            String num = numberField.getText().trim();
            String type = (String) typeBox.getSelectedItem();

            if (num.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter vehicle number!");
                return;
            }

            Vehicle v = switch (type) {
                case "Car" -> new Car(num);
                case "Bike" -> new Bike(num);
                case "Truck" -> new Truck(num);
                default -> null;
            };

            if (lot.parkVehicle(v)) {
                String formattedTime = formatTimestamp(v.getEntryTime());
                tableModel.addRow(new Object[]{v.getNumber(), v.getType(), formattedTime});
                numberField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle already parked or invalid.");
            }
        });

        removeButton.addActionListener((ActionEvent e) -> {
            String num = removeField.getText().trim();
            Vehicle v = lot.removeVehicle(num);
            if (v != null) {
                double charges = v.calculateCharge();
                resultLabel.setText("Charges: ₹" + String.format("%.2f", charges));
                removeField.setText("");

                // Remove from table
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(num)) {
                        tableModel.removeRow(i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vehicle not found.");
            }
        });

        printBillButton.addActionListener((ActionEvent e) -> {
            String num = removeField.getText().trim();
            Vehicle v = lot.getVehicle(num);

            if (v != null) {
                long currentTime = System.currentTimeMillis();
                long durationMillis = currentTime - v.getEntryTime();
                long minutes = durationMillis / (60 * 1000);
                long hours = minutes / 60;
                minutes = minutes % 60;

                double charges = v.calculateCharge();

                String bill = """
                        === Parking Bill ===
                        Vehicle Number: %s
                        Type: %s
                        Entry Time: %s
                        Exit Time: %s
                        Time Spent: %d hours %d minutes
                        Charges: ₹%.2f
                        """.formatted(
                        v.getNumber(),
                        v.getType(),
                        formatTimestamp(v.getEntryTime()),
                        formatTimestamp(currentTime),
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

    // Helper method to format timestamp
    private String formatTimestamp(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        Date resultDate = new Date(millis);
        return sdf.format(resultDate);
    }

    public static void main(String[] args) {
        new ParkingApp();
    }
}
