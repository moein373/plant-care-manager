package com.moein.plantcare.controller;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.moein.plantcare.model.Plant;
import com.moein.plantcare.repository.PlantRepository;
import com.moein.plantcare.view.PlantView;

public class PlantControllerTest {

	@Mock
	private PlantRepository plantRepository;

	@Mock
	private PlantView plantView;

	@InjectMocks
	private PlantController plantController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testAllPlants() {
		List<Plant> plants = asList(new Plant("P001", "Monstera", "Living Room", 7, LocalDate.of(2000, 1, 3)));

		when(plantRepository.findAll()).thenReturn(plants);

		plantController.allPlants();

		verify(plantView).showAllPlants(plants);
	}

	@Test
	public void testNewPlantWhenPlantDoesNotAlreadyExist() {
		Plant plant = new Plant("P001", "Monstera", "Living Room", 7, LocalDate.of(2000, 1, 3));

		when(plantRepository.findById("P001")).thenReturn(null);

		plantController.newPlant(plant);

		InOrder inOrder = inOrder(plantRepository, plantView);

		inOrder.verify(plantRepository).save(plant);
		inOrder.verify(plantView).plantAdded(plant);
	}
}