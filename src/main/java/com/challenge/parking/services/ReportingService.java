package com.challenge.parking.services;

import com.challenge.parking.dto.Parking;
import com.challenge.parking.dto.Report;
import com.challenge.parking.model.ParkingRepository;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author Fahad Sarwar
 */
@Component
public class ReportingService {

	private final ParkingRepository parkingRepository;

	public ReportingService(ParkingRepository parkingRepository) {

		this.parkingRepository = parkingRepository;
	}

	public Map<Long, Map<LocalDate, Report>> generateReport(Long id) {

		List<Parking> parkings = new LinkedList<>();
		if (id != null) {
			parkings.add(parkingRepository.findById(id));
		} else {
			parkings.addAll(parkingRepository.getAllParking());
		}
		return parkings.stream().collect(Collectors.toMap(Parking::getId, Parking::getDailyReport, (a, b) -> b, TreeMap::new));
	}

	void updateDailyReport(Parking parking, double charges) {

		Map<LocalDate, Report> dailyReport = parking.getDailyReport();
		LocalDate now = LocalDate.now();
		Report report = dailyReport.get(now);
		double totalEarning = 0D;
		long numberOfCarsParked = 0L;
		if (report != null) {
			numberOfCarsParked = report.getNumberOfCarsParked();
			totalEarning = report.getTotalEarning();
		} else {
			report = new Report(0L, 0D);
		}
		numberOfCarsParked += 1;
		totalEarning += charges;
		report.setNumberOfCarsParked(numberOfCarsParked);
		report.setTotalEarning(totalEarning);
		dailyReport.put(now, report);
		parking.setDailyReport(dailyReport);
	}
}
