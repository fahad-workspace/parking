package com.example.parking.dto;

public class ParkingCharges implements Comparable<ParkingCharges> {

	private long hour;

	private double rate;

	public ParkingCharges(long hour, double rate) {

		this.hour = hour;
		this.rate = rate;
	}

	public long getHour() {

		return hour;
	}

	public void setHour(long hour) {

		this.hour = hour;
	}

	public double getRate() {

		return rate;
	}

	public void setRate(double rate) {

		this.rate = rate;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ParkingCharges that = (ParkingCharges) o;
		return hour == that.hour;
	}

	@Override
	public int hashCode() {

		return (int) (hour ^ (hour >>> 32));
	}

	@Override
	public int compareTo(ParkingCharges that) {

		if (this.hour > that.hour) {
			return -1;
		} else if (this.hour < that.hour) {
			return 1;
		}
		if (this.rate > that.rate) {
			return -1;
		} else if (this.rate < that.rate) {
			return 1;
		}
		return 0;
	}
}
