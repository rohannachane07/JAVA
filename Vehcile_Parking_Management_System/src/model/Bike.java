package model;

public class Bike extends  Vehicle
{
    public Bike(String name)
    {
        super(name);
        this.type="Bike";
    }

    @Override
    public double calculateCharge() {
        long durationInMilliSec=(System.currentTimeMillis()-entryTime);
        double durationInMin=durationInMilliSec/(1000.0*60);
        return 0.7* Math.ceil(durationInMin);  //If a vehicle stayed for 1 minute and 30 seconds, it should still be charged for 2 minutes (rounding up), just like in real-world parking systems.
    }
}
