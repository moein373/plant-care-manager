package com.moein.plantcare.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;

public class PlantTest {

	private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2026-11-26T12:00:00Z"), ZoneOffset.UTC);

	private Plant plant;

	@Before
	public void setUp() {
		plant = new Plant("P001", "Monstera", "Living Room", 7, LocalDate.of(2026, 11, 20));
	}

	@Test
	public void testPlantId() {
		assertEquals("P001", plant.getId());
	}

	@Test
	public void testPlantName() {
		assertEquals("Monstera", plant.getName());
	}

	@Test
	public void testPlantLocation() {
		assertEquals("Living Room", plant.getLocation());
	}

	@Test
	public void testPlantWateringIntervalDays() {
		assertEquals(7, plant.getWateringIntervalDays());
	}

	@Test
	public void testPlantLastWateredDate() {
		assertEquals(LocalDate.of(2026, 11, 20), plant.getLastWateredDate());
	}

	@Test
	public void testPlantWithNullId() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant(null, "Monstera", "Living Room", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithEmptyId() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("", "Monstera", "Living Room", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithBlankId() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("   ", "Monstera", "Living Room", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithNullName() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", null, "Living Room", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithEmptyName() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "", "Living Room", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithBlankName() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "   ", "Living Room", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithNullLocation() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", null, 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithEmptyLocation() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", "", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithBlankLocation() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", "   ", 7, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithZeroWateringInterval() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", "Living Room", 0, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithNegativeWateringInterval() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", "Living Room", -1, LocalDate.of(2026, 11, 20)));
	}

	@Test
	public void testPlantWithMinimumWateringInterval() {
		Plant plant = new Plant("P001", "Monstera", "Living Room", 1, LocalDate.of(2026, 11, 20));

		assertEquals(1, plant.getWateringIntervalDays());
	}

	@Test
	public void testPlantWithNullLastWateredDate() {
		assertThrows(IllegalArgumentException.class, () -> new Plant("P001", "Monstera", "Living Room", 7, null));
	}

	@Test
	public void testPlantWithFutureLastWateredDate() {
		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", "Living Room", 7, LocalDate.MAX));
	}

	@Test
	public void testPlantWithTodayAsLastWateredDate() {
		LocalDate today = LocalDate.now(FIXED_CLOCK);

		Plant plant = new Plant("P001", "Monstera", "Living Room", 7, today, FIXED_CLOCK);

		assertEquals(today, plant.getLastWateredDate());
	}

	@Test
	public void testPlantWithTomorrowAsLastWateredDate() {
		LocalDate tomorrow = LocalDate.now(FIXED_CLOCK).plusDays(1);

		assertThrows(IllegalArgumentException.class,
				() -> new Plant("P001", "Monstera", "Living Room", 7, tomorrow, FIXED_CLOCK));
	}
}