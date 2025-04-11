package model;

public class Car extends Vehicle {
    public Car(String number) {
        super(number);
        this.type = "Car";
    }

    @Override
    public double calculateCharge() {
        long duration = (System.currentTimeMillis() - entryTime) / 1000; // in seconds
        return 20 + duration * 0.1;
    }
}
