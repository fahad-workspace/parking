package com.example.parking.controllers;

import com.example.parking.data.ParkingData;
import com.example.parking.dto.Parking;
import com.example.parking.services.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/parking")
public class ParkingController {

	private final ParkingService service;

	public ParkingController(ParkingService service) {

		this.service = service;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createParking(@RequestBody ParkingData parkingData) {

		Parking parking = service.createParking(parkingData);
		return new ResponseEntity<>(parking, HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAvailableBays(@RequestParam("id") Long id) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			long free = service.getAvailableBays(parking);
			return new ResponseEntity<>(free, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> parkCar(@RequestParam("id") Long id) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			Integer index = service.parkCar(parking);
			return new ResponseEntity<>(index, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/print", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> print(@RequestParam("id") Long id) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			String parkingMap = service.printParking(parking);
			return new ResponseEntity<>(parkingMap, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/unpark", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> unparkCar(@RequestParam("id") Long id, @RequestParam("index") Integer index) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			boolean unparked = service.unparkCar(parking, index);
			return new ResponseEntity<>(unparked, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
