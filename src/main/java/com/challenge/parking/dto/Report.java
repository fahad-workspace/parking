package com.challenge.parking.dto;

import java.io.Serializable;

public class Report implements Serializable {

	private static final long serialVersionUID = 7886618884214461544L;

	private long numberOfCarsParked;

	private double totalEarning;

	public Report(long numberOfCarsParked, double totalEarning) {

		this.numberOfCarsParked = numberOfCarsParked;
		this.totalEarning = totalEarning;
	}

	public long getNumberOfCarsParked() {

		return numberOfCarsParked;
	}

	public void setNumberOfCarsParked(long numberOfCarsParked) {

		this.numberOfCarsParked = numberOfCarsParked;
	}

	public double getTotalEarning() {

		return totalEarning;
	}

	public void setTotalEarning(double totalEarning) {

		this.totalEarning = totalEarning;
	}
}
