package com.challenge.parking.dto;

import java.io.Serializable;

public class ParkingCharges implements Serializable, Comparable<ParkingCharges> {

	private static final long serialVersionUID = -9180034320511521234L;

	private long hour;

	private double rate;

	public ParkingCharges(long hour, double rate) {

		this.hour = hour;
		this.rate = rate;
	}

	@Override
	public int compareTo(ParkingCharges that) {

		if (hour > that.hour) {
			return -1;
		} else if (hour < that.hour) {
			return 1;
		}
		if (rate > that.rate) {
			return -1;
		} else if (rate < that.rate) {
			return 1;
		}
		return 0;
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
	public int hashCode() {

		return (int) (hour ^ (hour >>> 32));
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		ParkingCharges that = (ParkingCharges) o;
		return hour == that.hour;
	}
}
