package com.example.parking.dto;

import java.util.HashSet;
import java.util.Set;

public class Parking {

	private Long id;

	private Integer size;

	private Set<ParkingBay> bays = new HashSet<>();

	public Parking() {

		super();
	}

	public Parking(Integer size) {

		super();
		this.size = size;
	}

	public void addBay(ParkingBay bay) {

		getBays().add(bay);
	}

	public Set<ParkingBay> getBays() {

		return bays;
	}

	public void setBays(Set<ParkingBay> bays) {

		this.bays = bays;
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public Integer getSize() {

		return size;
	}

	public void setSize(Integer size) {

		this.size = size;
	}
}
