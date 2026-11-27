package com.moein.plantcare.repository.mongo;

import java.util.List;

import com.mongodb.client.MongoClient;
import com.moein.plantcare.model.Plant;
import com.moein.plantcare.repository.PlantRepository;

public class PlantMongoRepository implements PlantRepository {

	public PlantMongoRepository(MongoClient client, String databaseName, String collectionName) {
	}

	@Override
	public List<Plant> findAll() {
		return null;
	}

	@Override
	public Plant findById(String id) {
		return null;
	}

	@Override
	public void save(Plant plant) {
	}

	@Override
	public void delete(String id) {
	}
}