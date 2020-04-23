package com.challenge.parking;

import static org.junit.Assert.assertEquals;

import com.challenge.parking.dto.Parking;
import com.challenge.parking.dto.ParkingBuilder;
import com.challenge.parking.services.ParkingService;
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
public class ParkingBuilderTest {

	@Autowired
	private ParkingService service;

	@Test
	public void testBuildBasicParking() {

		final Parking parking = new ParkingBuilder().withSquareSize(4).build();
		assertEquals(16, service.getAvailableBays(parking));
	}

	@Test
	public void testBuildParkingWithMultiplePedestrianExits() {

		final Parking parking = new ParkingBuilder().withSquareSize(10).withPedestrianExit(8).withPedestrianExit(42).withPedestrianExit(85).build();
		assertEquals(97, service.getAvailableBays(parking));
	}

	@Test
	public void testBuildParkingWithPedestrianExit() {

		final Parking parking = new ParkingBuilder().withSquareSize(3).withPedestrianExit(5).build();
		assertEquals(8, service.getAvailableBays(parking));
	}
}
