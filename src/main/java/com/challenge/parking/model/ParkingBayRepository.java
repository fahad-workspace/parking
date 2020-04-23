package com.challenge.parking.model;

import com.challenge.parking.dto.ParkingBay;
import org.springframework.stereotype.Repository;

/**
 * @author Fahad Sarwar
 */
@Repository
public interface ParkingBayRepository {

	ParkingBay findByParkingIdAndIndex(Long id, Integer index);

	void saveAndFlush(ParkingBay parkingBay);
}
