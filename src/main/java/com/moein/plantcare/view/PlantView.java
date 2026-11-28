package com.moein.plantcare.view;

import java.util.List;

import com.moein.plantcare.model.Plant;

public interface PlantView {

	void showAllPlants(List<Plant> plants);

	void plantAdded(Plant plant);

	void showError(String message, Plant plant);

}