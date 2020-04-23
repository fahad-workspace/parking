package com.example.parking.dto;

import java.util.ArrayList;

/**
 * Builder class to get a parking instance
 */
public class ParkingBuilder {

	private final ArrayList<Integer> pedestrianExits = new ArrayList<>();

	private Integer size;

	public Parking build() {

		Parking parking = new Parking(size);
		ParkingBay bay;
		for (int i = 0; i < (size * size); i++) {
			bay = new ParkingBay(i, pedestrianExits.contains(i), parking);
			parking.addBay(bay);
		}
		pedestrianExits.forEach(peIndex -> parking.getBays().forEach(p -> p.setDistanceToExit(Integer.min(p.getDistanceToExit(), Math.abs(peIndex - p.getIndex())))));
		return parking;
	}

	public ParkingBuilder withPedestrianExit(int pedestrianExitIndex) {

		pedestrianExits.add(pedestrianExitIndex);
		return this;
	}

	public ParkingBuilder withSquareSize(int size) {

		this.size = size;
		return this;
	}
}
