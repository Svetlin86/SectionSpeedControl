package com.sectionspeedcontrol.sectionspeedcontrol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadVehicleData {

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

}
