package com.vastidev.parkingcontrol.controllers;


import com.vastidev.parkingcontrol.dtos.ParkingSpotDto;
import com.vastidev.parkingcontrol.models.ParkingSpotModel;
import com.vastidev.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/parking-spot")

public class ParkingSpotcontroller {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotcontroller(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParking(@RequestBody @Valid ParkingSpotDto parkingSpotDto){

        String validationError = validateParkingSpot(parkingSpotDto);
        if (validationError != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationError);
        }
        var parkingSpotModel = new ParkingSpotModel(parkingSpotDto);
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate((LocalDateTime.now(ZoneId.of("UTC"))));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }

//    @PostMapping
//    public ResponseEntity<ParkingSpotModel> insert(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
//        ParkingSpotModel newParkingSpotModel = this.parkingSpotService.insert(parkingSpotDto);
//        return ResponseEntity.ok().body(newParkingSpotModel);
//    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }


    private String validateParkingSpot(ParkingSpotDto parkingSpotDto){
        if(parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
            return"Conflict: License Plate Car is already in use!";
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return "Conflict: Parking Spot is already in use!";
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return "Conflict: Parking Spot already registered for this apartment and block";
        }
        return null;

    }
}
