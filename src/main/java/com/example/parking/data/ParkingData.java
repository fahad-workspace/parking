package com.example.parking.data;

import java.util.ArrayList;
import java.util.List;

public class ParkingData {

	private Integer size;

	private List<Integer> pedestrianExits = new ArrayList<>();

	public ParkingData() {

		super();
	}

	public List<Integer> getPedestrianExits() {

		return pedestrianExits;
	}

	public void setPedestrianExits(List<Integer> pedestrianExits) {

		this.pedestrianExits = pedestrianExits;
	}

	public Integer getSize() {

		return size;
	}

	public void setSize(Integer size) {

		this.size = size;
	}
}
