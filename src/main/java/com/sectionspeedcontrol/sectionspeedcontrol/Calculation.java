package com.sectionspeedcontrol.sectionspeedcontrol;

import java.time.LocalTime;
import java.util.List;

public class Calculation {

    public static void displayExerciseNumber(int exerciseNumber) {
        System.out.println("Exercise " + exerciseNumber + ".");
    }

    public static int countVehiclesBeforeTime(List<Vehicle> vehicles, LocalTime time) {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getExitTime().isBefore(time)) {
                count++;
            }
        }
        return count;
    }

    public static void countVehiclesAtGivenTime(List<Vehicle> vehicles, int hour, int minute) {
        int vehiclesInGivenMinute = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getEntryTime().getHour() == hour && vehicle.getEntryTime().getMinute() == minute) {
                vehiclesInGivenMinute++;
            }
        }
        System.out.println("a. The number of vehicles that passed the entry point recorder: " + vehiclesInGivenMinute);

        double trafficIntensity = (double) vehiclesInGivenMinute / 10.0;

        System.out.println("b. The traffic intensity: " + trafficIntensity);
    }

    public static void findVehicleWithHighestSpeed(List<Vehicle> vehicles) {
        Vehicle fastestVehicle = vehicles.get(0);
        int overtakenCount = 0;

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getAverageSpeed() > fastestVehicle.getAverageSpeed()) {
                fastestVehicle = vehicle;
                overtakenCount = 0;
            } else if (vehicle.getAverageSpeed() == fastestVehicle.getAverageSpeed() && !vehicle.equals(fastestVehicle)) {
                overtakenCount++;
            }
        }

        System.out.println("The data of the vehicle with the highest speed are");
        System.out.println("license plate number: " + fastestVehicle.getLicensePlate());
        System.out.println("average speed: " + (int) fastestVehicle.getAverageSpeed() + " km/h");
        System.out.println("number of overtaken vehicles: " + overtakenCount);
    }

    public static double calculatePercentExceedingLimit(List<Vehicle> vehicles) {
        int countExceedingLimit = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getAverageSpeed() > 90.0) {
                countExceedingLimit++;
            }
        }
        return (double) countExceedingLimit / vehicles.size();
    }
}
