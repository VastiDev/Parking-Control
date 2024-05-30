package com.vastidev.parkingcontrol.services;

import com.vastidev.parkingcontrol.dtos.ParkingSpotDto;
import com.vastidev.parkingcontrol.models.ParkingSpotModel;
import com.vastidev.parkingcontrol.repositories.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;


    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

//    public ParkingSpotModel insert(ParkingSpotDto parkingSpotDto) {
//        ParkingSpotModel newparkingSpotModel = new ParkingSpotModel(parkingSpotDto);
//        this.parkingSpotRepository.save(newparkingSpotModel);
//        return newparkingSpotModel;
//    }
}
