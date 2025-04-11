package model;

public abstract class Vehicle {
    protected String number;
    protected String type;
    protected long entryTime;

    public Vehicle(String number) {
        this.number = number;
        this.entryTime = System.currentTimeMillis();
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public abstract double calculateCharge();
}
