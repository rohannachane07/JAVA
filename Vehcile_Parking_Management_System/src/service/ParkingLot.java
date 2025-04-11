package service;

import model.Vehicle;

import java.util.HashMap;

public class ParkingLot {
    private HashMap<String, Vehicle> parkedVehicles = new HashMap<>();

    public boolean parkVehicle(Vehicle vehicle) {
        if (!parkedVehicles.containsKey(vehicle.getNumber())) {
            parkedVehicles.put(vehicle.getNumber(), vehicle);
            return true;
        }
        return false;
    }

    public Vehicle removeVehicle(String number) {
        return parkedVehicles.remove(number);
    }

    public  HashMap<String, Vehicle> getAllVehicles() {
        return parkedVehicles;
    }

    public Vehicle getVehicle(String number) {
        return parkedVehicles.get(number);
    }
    public Vehicle findVehicle(String number) {
        return parkedVehicles.get(number);
    }

}
