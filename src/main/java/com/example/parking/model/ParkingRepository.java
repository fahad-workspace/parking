package com.example.parking.model;

import com.example.parking.dto.Parking;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository {

	Parking findById(Long id);

	List<Parking> getAllParking();

	void save(Parking parking);
}
