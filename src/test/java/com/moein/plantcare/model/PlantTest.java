package com.moein.plantcare.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class PlantTest {

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
}