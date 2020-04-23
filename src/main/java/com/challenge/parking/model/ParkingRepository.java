package com.challenge.parking.model;

import com.challenge.parking.dto.Parking;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository {

	Parking findById(Long id);

	List<Parking> getAllParking();

	void save(Parking parking);
}
