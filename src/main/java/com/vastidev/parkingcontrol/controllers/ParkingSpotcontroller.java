package com.vastidev.parkingcontrol.controllers;


import com.vastidev.parkingcontrol.dtos.ParkingSpotDto;
import com.vastidev.parkingcontrol.models.ParkingSpotModel;
import com.vastidev.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/parking-spot")

public class ParkingSpotcontroller {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotcontroller(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParking(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
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



}
