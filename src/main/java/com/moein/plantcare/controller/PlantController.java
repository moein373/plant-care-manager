package com.moein.plantcare.controller;

import com.moein.plantcare.repository.PlantRepository;
import com.moein.plantcare.view.PlantView;

public class PlantController {

	private final PlantView plantView;
	private final PlantRepository plantRepository;

	public PlantController(PlantView plantView, PlantRepository plantRepository) {
		this.plantView = plantView;
		this.plantRepository = plantRepository;
	}

	public void allPlants() {

	}
}