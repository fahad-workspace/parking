package com.example.parking.model;

import com.example.parking.dto.ParkingBay;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBayRepository {

	ParkingBay findByParkingIdAndIndex(Long id, Integer index);

	void saveAndFlush(ParkingBay parkingBay);
}
