package com.moein.plantcare.repository;

import java.util.List;

import com.moein.plantcare.model.Plant;

public interface PlantRepository {

	List<Plant> findAll();

	Plant findById(String id);

	void save(Plant plant);

	void delete(String id);
}