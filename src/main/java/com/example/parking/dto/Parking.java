package com.example.parking.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Parking {

	private Long id;

	private Integer size;

	private Map<LocalDate, Report> dailyReport = new TreeMap<>();

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

	public Map<LocalDate, Report> getDailyReport() {

		return dailyReport;
	}

	public void setDailyReport(Map<LocalDate, Report> dailyReport) {

		this.dailyReport = dailyReport;
	}
}
