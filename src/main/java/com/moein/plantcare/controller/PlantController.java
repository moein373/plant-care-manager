package com.moein.plantcare.controller;

import com.moein.plantcare.model.Plant;
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
		plantView.showAllPlants(plantRepository.findAll());
	}

	public void newPlant(Plant plant) {
		Plant existingPlant = plantRepository.findById(plant.getId());

		if (existingPlant != null) {
			plantView.showError("Already existing plant with id " + plant.getId(), existingPlant);
			return;
		}

		plantRepository.save(plant);
		plantView.plantAdded(plant);
	}

	public void deletePlant(Plant plant) {
		if (plantRepository.findById(plant.getId()) != null) {
			plantRepository.delete(plant.getId());
			plantView.plantRemoved(plant);
		}
	}
}