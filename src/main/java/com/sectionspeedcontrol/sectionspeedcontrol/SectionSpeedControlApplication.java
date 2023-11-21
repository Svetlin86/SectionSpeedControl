package com.sectionspeedcontrol.sectionspeedcontrol;

import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.calculatePercentExceedingLimit;
import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.countVehiclesAtGivenTime;
import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.countVehiclesBeforeTime;
import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.displayExerciseNumber;
import static com.sectionspeedcontrol.sectionspeedcontrol.Calculation.findVehicleWithHighestSpeed;
import static com.sectionspeedcontrol.sectionspeedcontrol.VehicleDataConversion.readVehicleData;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SectionSpeedControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SectionSpeedControlApplication.class, args);
		String filePath = Objects.requireNonNull(VehicleDataConversion.class.getClassLoader().getResource("measurements.txt")).getFile();
		List<Vehicle> vehicles = readVehicleData(filePath);

		displayExerciseNumber(1);
		System.out.println("The data of " + vehicles.size() + " vehicles were recorded in the measurement.");

		displayExerciseNumber(2);
		int vehiclesBeforeNine = countVehiclesBeforeTime(vehicles, LocalTime.of(9, 0));
		System.out.println("Before 9 o'clock " + vehiclesBeforeNine + " vehicles passed the exit point recorder.");

		displayExerciseNumber(3);
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter an hour and minute value: ");
		int hour = scanner.nextInt();
		int minute = scanner.nextInt();
		countVehiclesAtGivenTime(vehicles, hour, minute);

		displayExerciseNumber(4);
		findVehicleWithHighestSpeed(vehicles);

		displayExerciseNumber(5);
		double percentExceedingSpeedLimit = calculatePercentExceedingLimit(vehicles);
		System.out.println("Percent of vehicles exceeding the speed limit: " + percentExceedingSpeedLimit);
	}
}
