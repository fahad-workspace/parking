package com.example.parking.model.impl;

import com.example.parking.cache.SimpleCacheService;
import com.example.parking.dto.Parking;
import com.example.parking.model.ParkingRepository;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingRepositoryImpl implements ParkingRepository {

	private final Logger logger = LogManager.getLogger(ParkingRepositoryImpl.class);

	private final SimpleCacheService simpleCacheService;

	public ParkingRepositoryImpl(SimpleCacheService simpleCacheService) {

		this.simpleCacheService = simpleCacheService;
	}

	@Override
	public Parking findById(Long id) {

		Map<Long, Parking> parkingMap = simpleCacheService.getParkingMap();
		Parking parking = parkingMap.get(id);
		logger.info("Found :: " + parking);
		return parking;
	}

	@Override
	public void save(Parking parking) {

		Map<Long, Parking> parkingMap = simpleCacheService.getParkingMap();
		Set<Long> coll = parkingMap.keySet();
		long max = 1L;
		if (CollectionUtils.isNotEmpty(coll)) {
			max = Collections.max(coll) + 1;
		}
		parking.setId(max);
		parkingMap.put(parking.getId(), parking);
		simpleCacheService.setParkingMap(parkingMap);
		logger.info("Saved :: " + parking.toString());
	}
}