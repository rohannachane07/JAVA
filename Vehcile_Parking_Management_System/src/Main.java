import model.*;
import service.ParkingLot;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Park Vehicle\n2. Remove Vehicle\n3. Show All Vehicles\n4. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Vehicle Number: ");
                    String num = sc.nextLine();
                    System.out.print("Enter Type (Car/Bike/Truck): ");
                    String type = sc.nextLine().toLowerCase();

                    Vehicle v = switch (type) {
                        case "car" -> new Car(num);
                        case "bike" -> new Bike(num);
                        case "truck" -> new Truck(num);
                        default -> null;
                    };

                    if (v != null && lot.parkVehicle(v)) {
                        System.out.println(type + " parked successfully.");
                    } else {
                        System.out.println("Invalid type or already parked.");
                    }
                }

                case 2 -> {
                    System.out.print("Enter Vehicle Number to remove: ");
                    String num = sc.nextLine();
                    Vehicle v = lot.removeVehicle(num);
                    if (v != null) {
                        double charges = v.calculateCharge();
                        System.out.println("Vehicle Removed. Charges: ₹" + charges);
                    } else {
                        System.out.println("Vehicle not found.");
                    }
                }

                case 3 -> {
                    for (Vehicle v : lot.getAllVehicles().values()) {
                        System.out.println(v.getType() + " - " + v.getNumber() + " (Entered at: " + v.getEntryTime() + ")");
                    }
                }

                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }
}
