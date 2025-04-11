package model;

public class Bike extends Vehicle {
    public Bike(String number) {
        super(number);
        this.type = "Bike";
    }

    @Override
    public double calculateCharge() {
        long duration = (System.currentTimeMillis() - entryTime) / 1000;
        return 10 + duration * 0.05;
    }
}
