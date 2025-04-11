package model;

public class Truck extends Vehicle {
    public Truck(String number) {
        super(number);
        this.type = "Truck";
    }

    @Override
    public double calculateCharge() {
        long duration = (System.currentTimeMillis() - entryTime) / 1000;
        return 30 + duration * 0.15;
    }
}
