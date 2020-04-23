package com.challenge.parking.cache;

import com.challenge.parking.dto.Parking;
import com.challenge.parking.dto.ParkingBay;
import com.challenge.parking.dto.ParkingBayKey;
import com.challenge.parking.dto.ParkingCharges;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Fahad Sarwar
 */
@Service
public class SimpleCacheService {

	private final Set<ParkingCharges> weekdayCharges = new TreeSet<>();

	private final Set<ParkingCharges> weekendCharges = new TreeSet<>();

	@Value("${weekday.charges}")
	private String weekdayRates;

	@Value("${weekend.charges}")
	private String weekendRates;

	private Map<Long, Parking> parkingMap = new LinkedHashMap<>();

	public Map<ParkingBayKey, ParkingBay> getParkingBayMap(Long id, Integer index) {

		Parking parking = parkingMap.get(id);
		ParkingBay bay = null;
		Map<ParkingBayKey, ParkingBay> parkingBayMap = null;
		if (parking != null) {
			Set<ParkingBay> bays = parking.getBays();
			bay = bays.stream().filter(parkingBay -> parkingBay.getIndex().equals(index)).findFirst().orElse(null);
		}
		if (bay != null) {
			ParkingBayKey parkingBayKey = new ParkingBayKey(id, index);
			parkingBayMap = new LinkedHashMap<>();
			parkingBayMap.put(parkingBayKey, bay);
		}
		return parkingBayMap;
	}

	public Map<Long, Parking> getParkingMap() {

		return parkingMap;
	}

	public void setParkingMap(Map<Long, Parking> parkingMap) {

		this.parkingMap = parkingMap;
	}

	public Set<ParkingCharges> getWeekdayCharges() {

		return weekdayCharges;
	}

	public Set<ParkingCharges> getWeekendCharges() {

		return weekendCharges;
	}

	@PostConstruct
	public void init() {

		Arrays.stream(weekdayRates.split(",")).map(StringUtils::trim)
				.map(charges -> new ParkingCharges(Integer.parseInt(StringUtils.trim(charges.split(":")[0])), Integer.parseInt(StringUtils.trim(charges.split(":")[1]))))
				.forEachOrdered(weekdayCharges::add);
		Arrays.stream(weekendRates.split(",")).map(StringUtils::trim)
				.map(charges -> new ParkingCharges(Integer.parseInt(StringUtils.trim(charges.split(":")[0])), Integer.parseInt(StringUtils.trim(charges.split(":")[1]))))
				.forEachOrdered(weekendCharges::add);
	}

	public void setParkingBayMap(Map<ParkingBayKey, ParkingBay> parkingBayMap) {

		parkingBayMap.keySet().forEach(parkingBayKey -> {
			Parking parking = parkingMap.get(parkingBayKey.getBayId());
			ParkingBay bay = parkingBayMap.get(parkingBayKey);
			parking.setBays(parking.getBays().stream().map(parkingBay -> Objects.equals(parkingBay.getIndex(), bay.getIndex()) ? bay : parkingBay)
					.collect(Collectors.toCollection(LinkedHashSet::new)));
			parkingMap.put(parkingBayKey.getBayId(), parking);
		});
	}
}
