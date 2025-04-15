package model;

import java.util.Map;

public class Car extends Vehicle
{
    public Car(String number){
        super(number);
        this.type="Car";
    }
    @Override
    public double calculateCharge() {
        long durationInMilliSec=(System.currentTimeMillis()-entryTime);
        double durationInMin=durationInMilliSec/(1000.0*60);  //use double division for better precision Otherwise, if the parking time is less than a minute, duration will be 0 due to integer division.
        return 0.5* Math.ceil(durationInMin);  //round up to nearest minute
    }
}
