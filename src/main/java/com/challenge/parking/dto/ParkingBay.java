package com.challenge.parking.dto;

import com.challenge.parking.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * @author Fahad Sarwar
 */
public class ParkingBay implements Serializable {

	private static final long serialVersionUID = -6266603908467437884L;

	private Long bayId;

	private Integer index;

	private boolean pedestrianExit = false;

	private Integer distanceToExit = Integer.MAX_VALUE;

	private char parkedCar;

	private LocalDateTime parkedTime;

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

		parkedCar = pedestrianExit ? Constants.PEDESTRIAN_EXIT : Constants.EMPTY;
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

	public LocalDateTime getParkedTime() {

		return parkedTime;
	}

	public void setParkedTime(LocalDateTime parkedTime) {

		this.parkedTime = parkedTime;
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

	@Override
	public String toString() {

		return new StringJoiner(", ", ParkingBay.class.getSimpleName() + "[", "]").add("bayId=" + bayId).add("index=" + index).add("pedestrianExit=" + pedestrianExit)
				.add("distanceToExit=" + distanceToExit).add("parkedCar=" + parkedCar).add("parkedTime=" + parkedTime).toString();
	}
}
