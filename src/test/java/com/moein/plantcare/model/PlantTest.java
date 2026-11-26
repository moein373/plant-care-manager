package com.moein.plantcare.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlantTest {

	@Test
	public void testPlantId() {
		Plant plant = new Plant("P001", "Monstera");
		assertEquals("P001", plant.getId());
	}

	@Test
	public void testPlantName() {
		Plant plant = new Plant("P001", "Monstera");
		assertEquals("Monstera", plant.getName());
	}
}