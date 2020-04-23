package com.challenge.parking.controllers;

import com.challenge.parking.data.ParkingData;
import com.challenge.parking.dto.Parking;
import com.challenge.parking.services.ParkingService;
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

		return new ResponseEntity<>(service.createParking(parkingData), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAvailableBays(@RequestParam("id") Long id) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			return new ResponseEntity<>(service.getAvailableBays(parking), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> parkCar(@RequestParam("id") Long id) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			return new ResponseEntity<>(service.parkCar(parking), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/print", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> print(@RequestParam("id") Long id) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			return new ResponseEntity<>(service.printParking(parking), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/unpark", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> unparkCar(@RequestParam("id") Long id, @RequestParam("index") Integer index) {

		Parking parking = service.getParkingById(id);
		if (parking != null) {
			return new ResponseEntity<>(service.unparkCar(parking, index), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
