package com.example.parking.services;

import com.example.parking.data.ParkingData;
import com.example.parking.dto.Parking;
import com.example.parking.dto.ParkingBay;
import com.example.parking.dto.ParkingBuilder;
import com.example.parking.model.ParkingBayRepository;
import com.example.parking.model.ParkingRepository;
import com.example.parking.utils.Constants;
import java.util.Comparator;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ParkingService {

	private final ParkingRepository parkingRepository;

	private final ParkingBayRepository parkingBayRepository;

	public ParkingService(ParkingRepository parkingRepository, ParkingBayRepository parkingBayRepository) {

		this.parkingRepository = parkingRepository;
		this.parkingBayRepository = parkingBayRepository;
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
			boolean seen = false;
			ParkingBay best = null;
			Comparator<ParkingBay> comparator = Comparator.comparing(ParkingBay::getDistanceToExit).thenComparing(ParkingBay::getIndex);
			for (ParkingBay parkingBay : parking.getBays()) {
				if (parkingBay.getParkedCar() == Constants.EMPTY && (!seen || comparator.compare(parkingBay, best) < 0)) {
					seen = true;
					best = parkingBay;
				}
			}
			Optional<ParkingBay> parkingBayOpt = seen ? Optional.of(best) : Optional.empty();
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
	public boolean unparkCar(Parking parking, Integer index) {

		Optional<ParkingBay> parkingBayOpt = parking.getBays().stream().filter(pb -> pb.getIndex().equals(index)).findFirst();
		if (parkingBayOpt.isPresent()) {
			ParkingBay parkingBay = parkingBayOpt.get();
			if (parkingBay.isAvailable() || parkingBay.isPedestrianExit()) {
				return false;
			}
			parkingBay.initializeParkedCar();
			parkingBayRepository.saveAndFlush(parkingBay);
			return true;
		}
		return false;
	}
}
