package com.example.parking.services;

import com.example.parking.cache.SimpleCacheService;
import com.example.parking.data.ParkingData;
import com.example.parking.dto.Parking;
import com.example.parking.dto.ParkingBay;
import com.example.parking.dto.ParkingBuilder;
import com.example.parking.dto.ParkingCharges;
import com.example.parking.model.ParkingBayRepository;
import com.example.parking.model.ParkingRepository;
import com.example.parking.utils.Constants;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ParkingService {

	private final ParkingRepository parkingRepository;

	private final ParkingBayRepository parkingBayRepository;

	private final SimpleCacheService simpleCacheService;

	private final ReportingService reportingService;

	public ParkingService(ParkingRepository parkingRepository, ParkingBayRepository parkingBayRepository, SimpleCacheService simpleCacheService,
			ReportingService reportingService) {

		this.parkingRepository = parkingRepository;
		this.parkingBayRepository = parkingBayRepository;
		this.simpleCacheService = simpleCacheService;
		this.reportingService = reportingService;
	}

	/**
	 * Creates a new Parking with the provided configuration
	 *
	 * @param parkingData
	 * @return a new Parking
	 */
	public Parking createParking(ParkingData parkingData) {

		ParkingBuilder parkingBuilder = new ParkingBuilder().withSquareSize(parkingData.getSize());
		for (Integer i : parkingData.getPedestrianExits()) {
			parkingBuilder.withPedestrianExit(i);
		}
		Parking parking = parkingBuilder.build();
		parkingRepository.save(parking);
		return parking;
	}

	/**
	 * Get number of availbale bays of the specified parking
	 *
	 * @param parking
	 * @return
	 */
	public long getAvailableBays(Parking parking) {

		return parking.getBays().stream().filter(ParkingBay::isAvailable).count();
	}

	/**
	 * Gets the parking with the provided id from DB
	 *
	 * @param id
	 * @return Parking
	 */
	public Parking getParkingById(Long id) {

		return parkingRepository.findById(id);
	}

	/**
	 * Park a car of the given type
	 *
	 * @param parking
	 * @return
	 */
	public Integer parkCar(Parking parking) {

		Integer indexToPark = getFirstAvailableBay(parking.getId());
		if (indexToPark >= 0) {
			Optional<ParkingBay> parkingBayOpt = parking.getBays().stream().filter(pb -> pb.getIndex().equals(indexToPark)).findFirst();
			if (parkingBayOpt.isPresent()) {
				ParkingBay parkingBay = parkingBayOpt.get();
				parkingBay.setBayId(parking.getId());
				parkingBay.setParkedCar(Constants.OCCUPIED);
				parkingBay.setParkedTime(LocalDateTime.now());
				parkingBayRepository.saveAndFlush(parkingBay);
			}
		}
		return indexToPark;
	}

	/**
	 * Get first available bay of the parking for a car
	 *
	 * @param parkingId
	 * @return
	 */
	private Integer getFirstAvailableBay(Long parkingId) {

		Parking parking = parkingRepository.findById(parkingId);
		if (parking != null) {
			Optional<ParkingBay> parkingBayOpt = parking.getBays().stream().filter(parkingBay -> parkingBay.getParkedCar() == Constants.EMPTY)
					.min(Comparator.comparing(ParkingBay::getDistanceToExit).thenComparing(ParkingBay::getIndex));
			if (parkingBayOpt.isPresent()) {
				return parkingBayOpt.get().getIndex();
			}
		}
		return -1;
	}

	/**
	 * Generates a String representation of the parking
	 *
	 * @param parking
	 * @return
	 */
	public String printParking(Parking parking) {

		StringBuilder strBuffParking = new StringBuilder();
		int totalSize = parking.getSize() * parking.getSize();
		boolean needReverse = false;
		for (int i = 0; i < totalSize; i = i + parking.getSize()) {
			Integer minIndex = i;
			Integer maxIndex = (i + parking.getSize()) - 1;
			StringBuffer strBuffLane = new StringBuffer();
			parking.getBays().stream().filter(pb -> ((pb.getIndex() >= minIndex) && (pb.getIndex() <= maxIndex))).sorted(Comparator.comparing(ParkingBay::getIndex))
					.forEachOrdered(pb -> strBuffLane.append(printBay(pb)));
			if (needReverse) {
				strBuffLane.reverse();
				needReverse = false;
			} else {
				needReverse = true;
			}
			if ((i + parking.getSize()) < totalSize) {
				strBuffLane.append("\n");
			}
			strBuffParking.append(strBuffLane);
		}
		return strBuffParking.toString();
	}

	private char printBay(ParkingBay parkingBay) {

		return parkingBay.getParkedCar();
	}

	/**
	 * Unpark a car from the given index
	 *
	 * @param parking
	 * @param index
	 * @return
	 */
	public double unparkCar(Parking parking, Integer index) {

		Optional<ParkingBay> parkingBayOpt = parking.getBays().stream().filter(pb -> pb.getIndex().equals(index)).findFirst();
		if (parkingBayOpt.isPresent()) {
			ParkingBay parkingBay = parkingBayOpt.get();
			if (parkingBay.isAvailable() || parkingBay.isPedestrianExit()) {
				return 0D;
			}
			double charges = getParkingCharges(parkingBay);
			reportingService.updateDailyReport(parking, charges);
			parkingBay.initializeParkedCar();
			parkingBayRepository.saveAndFlush(parkingBay);
			return charges;
		}
		return 0D;
	}

	private double getParkingCharges(ParkingBay parkingBay) {

		long hours = Duration.between(parkingBay.getParkedTime(), LocalDateTime.now()).toHours();
		double charges = 0D;
		charges = parkingBay.getParkedTime().getDayOfWeek() == DayOfWeek.SATURDAY || parkingBay.getParkedTime().getDayOfWeek() == DayOfWeek.SUNDAY ? getCharges(hours, charges,
				simpleCacheService.getWeekendCharges()) : getCharges(hours, charges, simpleCacheService.getWeekdayCharges());
		return charges;
	}

	private double getCharges(long hours, double charges, Set<ParkingCharges> chargesSet) {

		List<ParkingCharges> chargesList = new LinkedList<>(chargesSet);
		ParkingCharges parkingCharges = chargesList.stream().findFirst().orElse(null);
		if (parkingCharges != null) {
			if (hours > parkingCharges.getHour()) {
				charges = Math.ceil((double) hours / (double) parkingCharges.getHour()) * parkingCharges.getRate();
			} else {
				parkingCharges = chargesList.stream().filter(parkingCharge -> parkingCharge.getHour() > hours).reduce((first, second) -> second).orElse(null);
				if (parkingCharges != null) {
					charges = parkingCharges.getRate();
				}
			}
		}
		return charges;
	}
}
