package com.example.parking.dto;

import com.example.parking.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ParkingBay {

	private Long bayId;

	private Integer index;

	private boolean pedestrianExit = false;

	private Integer distanceToExit = Integer.MAX_VALUE;

	private char parkedCar;

	@JsonIgnore
	private Parking parking;

	public ParkingBay() {

		super();
	}

	public ParkingBay(Integer index) {

		super();
		this.index = index;
	}

	public ParkingBay(Integer index, boolean pedestrianExit, Parking parking) {

		super();
		this.index = index;
		this.pedestrianExit = pedestrianExit;
		this.parking = parking;
		initializeParkedCar();
	}

	public void initializeParkedCar() {

		if (pedestrianExit) {
			parkedCar = Constants.PEDESTRIAN_EXIT;
		} else {
			parkedCar = Constants.EMPTY;
		}
	}

	public Long getBayId() {

		return bayId;
	}

	public void setBayId(Long bayId) {

		this.bayId = bayId;
	}

	public Integer getDistanceToExit() {

		return distanceToExit;
	}

	public void setDistanceToExit(Integer distanceToExit) {

		this.distanceToExit = distanceToExit;
	}

	public Integer getIndex() {

		return index;
	}

	public void setIndex(Integer index) {

		this.index = index;
	}

	public char getParkedCar() {

		return parkedCar;
	}

	public void setParkedCar(char parkedCar) {

		this.parkedCar = parkedCar;
	}

	public Parking getParking() {

		return parking;
	}

	public void setParking(Parking parking) {

		this.parking = parking;
	}

	public boolean isAvailable() {

		return !isPedestrianExit() && (parkedCar == Constants.EMPTY);
	}

	public boolean isPedestrianExit() {

		return pedestrianExit;
	}

	public void setPedestrianExit(boolean pedestrianExit) {

		this.pedestrianExit = pedestrianExit;
	}
}
