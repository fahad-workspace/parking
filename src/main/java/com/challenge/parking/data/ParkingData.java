package com.challenge.parking.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkingData implements Serializable {

	private static final long serialVersionUID = 8846318259381738892L;

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
