package com.challenge.parking;

import static org.junit.Assert.assertEquals;

import com.challenge.parking.dto.Parking;
import com.challenge.parking.dto.ParkingBuilder;
import com.challenge.parking.model.ParkingRepository;
import com.challenge.parking.services.ParkingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Fahad Sarwar
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingTest {

	private Parking parking;

	@Autowired
	private ParkingService parkingService;

	@Autowired
	private ParkingRepository parkingRepository;

	@Before
	public void setUp() {

		parking = new ParkingBuilder().withSquareSize(5).withPedestrianExit(8).withPedestrianExit(12).build();
		parkingRepository.save(parking);
	}

	@Test
	public void testCompleteSolution() {

		assertEquals(Integer.valueOf(7), parkingService.parkCar(parking));
		assertEquals("EEEEE\nEXOEE\nEEXEE\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(22, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(9), parkingService.parkCar(parking));
		assertEquals("EEEEE\nOXOEE\nEEXEE\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(21, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(11), parkingService.parkCar(parking));
		assertEquals("EEEEE\nOXOEE\nEOXEE\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(20, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(13), parkingService.parkCar(parking));
		assertEquals("EEEEE\nOXOEE\nEOXOE\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(19, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(6), parkingService.parkCar(parking));
		assertEquals("EEEEE\nOXOOE\nEOXOE\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(18, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(10), parkingService.parkCar(parking));
		assertEquals("EEEEE\nOXOOE\nOOXOE\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(17, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(14), parkingService.parkCar(parking));
		assertEquals("EEEEE\nOXOOE\nOOXOO\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(16, parkingService.getAvailableBays(parking));
		assertEquals(1, parkingService.unparkCar(parking, 3), 2);
		assertEquals("EEEEE\nOXOOE\nOOXOO\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(16, parkingService.getAvailableBays(parking));
		assertEquals(7.0, parkingService.unparkCar(parking, 13), 2);
		assertEquals("EEEEE\nOXOOE\nOOXEO\nEEEEE\nEEEEE", parkingService.printParking(parking));
		assertEquals(17, parkingService.getAvailableBays(parking));
	}

	@Test
	public void testGetAvailableBays() {

		assertEquals(23, parkingService.getAvailableBays(parking));
	}

	@Test
	public void testParkCar() {

		assertEquals(Integer.valueOf(7), parkingService.parkCar(parking));
		assertEquals(22, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(9), parkingService.parkCar(parking));
		assertEquals(21, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(11), parkingService.parkCar(parking));
		assertEquals(20, parkingService.getAvailableBays(parking));
	}

	@Test
	public void testParkCarTwoVehicles() {

		assertEquals(Integer.valueOf(7), parkingService.parkCar(parking));
		assertEquals(22, parkingService.getAvailableBays(parking));
		assertEquals(Integer.valueOf(9), parkingService.parkCar(parking));
		assertEquals(21, parkingService.getAvailableBays(parking));
	}

	@Test
	public void testParkCarVehicleType() {

		assertEquals(Integer.valueOf(7), parkingService.parkCar(parking));
		assertEquals(22, parkingService.getAvailableBays(parking));
	}

	@Test
	public void testToString() {

		assertEquals("EEEEE\nEXEEE\nEEXEE\nEEEEE\nEEEEE", parkingService.printParking(parking));
	}

	@Test
	public void testUnparkCar() {

		final int firstCarBayIndex = parkingService.parkCar(parking);
		assertEquals(7.0, parkingService.unparkCar(parking, firstCarBayIndex), 2);
		assertEquals(23, parkingService.getAvailableBays(parking));
		assertEquals(1, parkingService.unparkCar(parking, firstCarBayIndex), 2);
		final int secondCarBayIndex = parkingService.parkCar(parking);
		assertEquals(7.0, parkingService.unparkCar(parking, secondCarBayIndex), 2);
		assertEquals(23, parkingService.getAvailableBays(parking));
		assertEquals(1, parkingService.unparkCar(parking, secondCarBayIndex), 2);
		assertEquals(1, parkingService.unparkCar(parking, 8), 2);
	}
}
