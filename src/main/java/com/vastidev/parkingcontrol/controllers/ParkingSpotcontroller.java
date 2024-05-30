package com.vastidev.parkingcontrol.controllers;


import com.vastidev.parkingcontrol.dtos.ParkingSpotDto;
import com.vastidev.parkingcontrol.dtos.UpdatedParkingSpotDto;
import com.vastidev.parkingcontrol.models.ParkingSpotModel;
import com.vastidev.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page=0,
    size=10, sort="id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerId(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully.");

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatingParkingSpot(@PathVariable(value = "id") UUID id,
                                                      @RequestBody @Valid UpdatedParkingSpotDto updatedParkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        ParkingSpotModel parkingSpotModel = new ParkingSpotModel(updatedParkingSpotDto);
        BeanUtils.copyProperties(updatedParkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate((LocalDateTime.now(ZoneId.of("UTC"))));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));

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
