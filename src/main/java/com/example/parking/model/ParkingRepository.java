package com.example.parking.model;

import com.example.parking.dto.Parking;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository {

	Parking findById(Long id);

	void save(Parking parking);
}
