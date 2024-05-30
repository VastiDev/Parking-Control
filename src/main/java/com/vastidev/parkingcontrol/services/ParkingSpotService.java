package com.vastidev.parkingcontrol.services;

import com.vastidev.parkingcontrol.dtos.ParkingSpotDto;
import com.vastidev.parkingcontrol.models.ParkingSpotModel;
import com.vastidev.parkingcontrol.repositories.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

//    public ParkingSpotModel insert(ParkingSpotDto parkingSpotDto) {
//        ParkingSpotModel newparkingSpotModel = new ParkingSpotModel(parkingSpotDto);
//        this.parkingSpotRepository.save(newparkingSpotModel);
//        return newparkingSpotModel;
//    }
}
