package model;

public class Truck extends Vehicle {
    public Truck(String name)
    {
        super(name);
        this.type="Truck";
    }

    @Override
    public double calculateCharge() {
        long durationInMilliSec=(System.currentTimeMillis()-entryTime);
        double durationInMin=durationInMilliSec/(1000.0*60);
        return 1* Math.ceil(durationInMin);  //If a vehicle stayed for 1 minute and 30 seconds, it should still be charged for 2 minutes (rounding up), just like in real-world parking systems.
    }
}
