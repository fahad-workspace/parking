package com.example.parking.cache;

import com.example.parking.dto.Parking;
import com.example.parking.dto.ParkingBay;
import com.example.parking.dto.ParkingBayKey;
import com.example.parking.dto.ParkingCharges;
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

@Service
public class SimpleCacheService {

	@Value("${weekday.charges}")
	private String weekdayRates;

	@Value("${weekend.charges}")
	private String weekendRates;

	private Map<Long, Parking> parkingMap = new LinkedHashMap<>();

	private final Set<ParkingCharges> weekdayCharges = new TreeSet<>();

	private final Set<ParkingCharges> weekendCharges = new TreeSet<>();

	@PostConstruct
	public void init() {

		Arrays.stream(weekdayRates.split(",")).map(StringUtils::trim).map(charges -> new ParkingCharges(Integer.parseInt(StringUtils.trim(charges.split(":")[0])),
				Integer.parseInt(StringUtils.trim(charges.split(":")[1])))).forEachOrdered(weekdayCharges::add);
		Arrays.stream(weekendRates.split(",")).map(StringUtils::trim).map(charges -> new ParkingCharges(Integer.parseInt(StringUtils.trim(charges.split(":")[0])),
				Integer.parseInt(StringUtils.trim(charges.split(":")[1])))).forEachOrdered(weekendCharges::add);
	}

	public Map<Long, Parking> getParkingMap() {

		return parkingMap;
	}

	public void setParkingMap(Map<Long, Parking> parkingMap) {

		this.parkingMap = parkingMap;
	}

	public Map<ParkingBayKey, ParkingBay> getParkingBayMap(Long id, Integer index) {

		Parking parking = parkingMap.get(id);
		ParkingBay bay = null;
		Map<ParkingBayKey, ParkingBay> parkingBayMap = null;
		if (parking != null) {
			Set<ParkingBay> bays = parking.getBays();
			for (ParkingBay parkingBay : bays) {
				if (parkingBay.getIndex().equals(index)) {
					bay = parkingBay;
					break;
				}
			}
		}
		if (bay != null) {
			ParkingBayKey parkingBayKey = new ParkingBayKey(id, index);
			parkingBayMap = new LinkedHashMap<>();
			parkingBayMap.put(parkingBayKey, bay);
		}
		return parkingBayMap;
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

	public Set<ParkingCharges> getWeekdayCharges() {

		return weekdayCharges;
	}

	public Set<ParkingCharges> getWeekendCharges() {

		return weekendCharges;
	}
}
