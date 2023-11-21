package com.sectionspeedcontrol.sectionspeedcontrol;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Vehicle {

    private String licensePlate;
    private LocalTime entryTime;
    private LocalTime exitTime;
    private double averageSpeed;

    public Vehicle(String licensePlate, LocalTime entryTime, LocalTime exitTime) {
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        calculateAverageSpeed();
    }

    private double calculateAverageSpeed() {
        double distance = 10.0;
        double elapsedTime = entryTime.until(exitTime, ChronoUnit.SECONDS);
        return averageSpeed = (distance / (360/elapsedTime))*10;
    }
}
