package com.sectionspeedcontrol.sectionspeedcontrol;

import static com.sectionspeedcontrol.sectionspeedcontrol.ReadVehicleData.readVehicleData;

import java.io.FileWriter;
import java.io.IOException;
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
		String filePath = Objects.requireNonNull(ReadVehicleData.class.getClassLoader().getResource("measurements.txt")).getFile();
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

	private static void displayExerciseNumber(int exerciseNumber) {
		System.out.println("Exercise " + exerciseNumber + ".");
	}

	private static int countVehiclesBeforeTime(List<Vehicle> vehicles, LocalTime time) {
		int count = 0;
		for (Vehicle vehicle : vehicles) {
			if (vehicle.getExitTime().isBefore(time)) {
				count++;
			}
		}
		return count;
	}

	private static void countVehiclesAtGivenTime(List<Vehicle> vehicles, int hour, int minute) {
		int vehiclesInGivenMinute = 0;
		for (Vehicle vehicle : vehicles) {
			if (vehicle.getEntryTime().getHour() == hour && vehicle.getEntryTime().getMinute() == minute) {
				vehiclesInGivenMinute++;
			}
		}
		System.out.println("a. The number of vehicles that passed the entry point recorder: " + vehiclesInGivenMinute);

		double trafficIntensity = (double) vehiclesInGivenMinute / 10.0; // Length of road section is 10 km
		System.out.println("b. The traffic intensity: " + trafficIntensity);
	}

	private static void findVehicleWithHighestSpeed(List<Vehicle> vehicles) {
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

	private static double calculatePercentExceedingLimit(List<Vehicle> vehicles) {
		int countExceedingLimit = 0;
		for (Vehicle vehicle : vehicles) {
			if (vehicle.getAverageSpeed() > 90.0) {
				countExceedingLimit++;
			}
		}
		return (double) countExceedingLimit / vehicles.size();
	}

	private static void writeResultsToFile(List<Vehicle> vehicles, String fileName) {
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
