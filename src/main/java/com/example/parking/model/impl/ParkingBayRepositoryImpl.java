package com.example.parking.model.impl;

import com.example.parking.cache.SimpleCacheService;
import com.example.parking.dto.ParkingBay;
import com.example.parking.dto.ParkingBayKey;
import com.example.parking.model.ParkingBayRepository;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingBayRepositoryImpl implements ParkingBayRepository {

	private final Logger logger = LogManager.getLogger(ParkingBayRepositoryImpl.class);

	private final SimpleCacheService simpleCacheService;

	public ParkingBayRepositoryImpl(SimpleCacheService simpleCacheService) {

		this.simpleCacheService = simpleCacheService;
	}

	@Override
	public ParkingBay findByParkingIdAndIndex(Long id, Integer index) {

		Map<ParkingBayKey, ParkingBay> parkingBayMap = simpleCacheService.getParkingBayMap(id, index);
		ParkingBayKey parkingBayKey = new ParkingBayKey(id, index);
		ParkingBay parkingBay = parkingBayMap.get(parkingBayKey);
		logger.info("Found by Parking Id and Index :: " + parkingBay);
		return parkingBay;
	}

	@Override
	public void saveAndFlush(ParkingBay parkingBay) {

		Map<ParkingBayKey, ParkingBay> parkingBayMap = simpleCacheService.getParkingBayMap(parkingBay.getBayId(), parkingBay.getIndex());
		ParkingBayKey parkingBayKey = new ParkingBayKey(parkingBay.getBayId(), parkingBay.getIndex());
		parkingBayMap.put(parkingBayKey, parkingBay);
		simpleCacheService.setParkingBayMap(parkingBayMap);
		logger.info("Saved and Flushed :: " + parkingBay.toString());
	}
}
