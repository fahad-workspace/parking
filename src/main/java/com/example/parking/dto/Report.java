package com.example.parking.dto;

public class Report {

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
