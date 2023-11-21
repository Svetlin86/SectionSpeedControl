package com.sectionspeedcontrol.sectionspeedcontrol;

import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.calculatePercentExceedingLimit;
import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.countVehiclesBeforeTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDataConversion implements DataConversion {

    public static List<Vehicle> readVehicleData(String filePath) {
        List<Vehicle> vehicles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");

                if (data.length == 9) {
                    String licensePlate = data[0];
                    LocalTime entryTime = LocalTime.of(
                            Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[3]), Integer.parseInt(data[4])
                    );
                    LocalTime exitTime = LocalTime.of(
                            Integer.parseInt(data[5]), Integer.parseInt(data[6]),
                            Integer.parseInt(data[7]), Integer.parseInt(data[8])
                    );

                    vehicles.add(new Vehicle(licensePlate, entryTime, exitTime));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public static void writeResultsToFile(List<Vehicle> vehicles, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Exercise 1.\nThe data of " + vehicles.size() + " vehicles were recorded in the measurement.\n\n");

            writer.write("Exercise 2.\nBefore 9 o'clock ");
            writer.write(countVehiclesBeforeTime(vehicles, LocalTime.of(9, 0)) + " vehicles passed the exit point recorder.\n\n");

            writer.write("Exercise 3.\nEnter an hour and minute value: (User input needed)\n");
            writer.write("a. The number of vehicles that passed the entry point recorder: (User input needed)\n");
            writer.write("b. The traffic intensity: (User input needed)\n\n");

            writer.write("Exercise 4.\nThe data of the vehicle with the highest speed are\n");
            writer.write("license plate number: (Highest speed vehicle's plate number)\n");
            writer.write("average speed: (Highest speed)\n");
            writer.write("number of overtaken vehicles: (Number of overtaken vehicles)\n\n");

            writer.write("Exercise 5.\nPercent of vehicles exceeding the speed limit: ");
            writer.write(calculatePercentExceedingLimit(vehicles) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
