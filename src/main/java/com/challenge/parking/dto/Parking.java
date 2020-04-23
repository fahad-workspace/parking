package com.challenge.parking.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;

public class Parking implements Serializable {

	private static final long serialVersionUID = -4633257513012532565L;

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

	public Map<LocalDate, Report> getDailyReport() {

		return dailyReport;
	}

	public void setDailyReport(Map<LocalDate, Report> dailyReport) {

		this.dailyReport = dailyReport;
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

	@Override
	public String toString() {

		return new StringJoiner(", ", Parking.class.getSimpleName() + "[", "]").add("id=" + id).add("size=" + size).add("dailyReport=" + dailyReport).add("bays=" + bays)
				.toString();
	}
}
