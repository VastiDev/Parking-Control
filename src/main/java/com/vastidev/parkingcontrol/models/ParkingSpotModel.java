package com.vastidev.parkingcontrol.models;

import com.vastidev.parkingcontrol.dtos.ParkingSpotDto;
import com.vastidev.parkingcontrol.dtos.UpdatedParkingSpotDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name= "TB_PARKING_SPOT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 10)
    private String parkingSpotNumber;
    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;
    @Column(nullable = false, length = 70)
    private String brandCar;
    @Column(nullable = false, length = 70)
    private String modelCar;
    @Column(nullable = false, length = 70)
    private String colorCar;
    @Column(nullable = false, length = 130)
    private String responsibleName;
    @Column(nullable = false, length = 30)
    private String apartment;
    @Column(nullable = false, length = 30)
    private String block;
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    public ParkingSpotModel(ParkingSpotDto parkingSpotDto) {
        this.parkingSpotNumber = parkingSpotDto.getParkingSpotNumber();
        this.licensePlateCar = parkingSpotDto.getLicensePlateCar();
        this.brandCar = parkingSpotDto.getBrandCar();
        this.modelCar = parkingSpotDto.getModelCar();
        this.colorCar = parkingSpotDto.getColorCar();
        this.responsibleName = parkingSpotDto.getResponsibleName();
        this.apartment = parkingSpotDto.getApartment();
        this.block = parkingSpotDto.getBlock();
        this.registrationDate = LocalDateTime.now();

    }

    public ParkingSpotModel(UpdatedParkingSpotDto updatedParkingSpotDto) {
        this.parkingSpotNumber = updatedParkingSpotDto.getParkingSpotNumber();
        this.licensePlateCar = updatedParkingSpotDto.getLicensePlateCar();
        this.brandCar = updatedParkingSpotDto.getBrandCar();
        this.modelCar = updatedParkingSpotDto.getModelCar();
        this.responsibleName = updatedParkingSpotDto.getResponsibleName();
        this.apartment = updatedParkingSpotDto.getApartment();
        this.block = updatedParkingSpotDto.getBlock();
        this.registrationDate = LocalDateTime.now();
    }
}