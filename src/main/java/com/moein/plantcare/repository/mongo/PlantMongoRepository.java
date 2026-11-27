package com.moein.plantcare.repository.mongo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.moein.plantcare.model.Plant;
import com.moein.plantcare.repository.PlantRepository;

public class PlantMongoRepository implements PlantRepository {

	private final MongoCollection<Document> plantCollection;

	public PlantMongoRepository(MongoClient client, String databaseName, String collectionName) {

		plantCollection = client.getDatabase(databaseName).getCollection(collectionName);
	}

	@Override
	public List<Plant> findAll() {
		return StreamSupport.stream(plantCollection.find().spliterator(), false)
				.map(document -> new Plant(document.getString("id"), document.getString("name"),
						document.getString("location"), document.getInteger("wateringIntervalDays"),
						LocalDate.parse(document.getString("lastWateredDate"))))
				.collect(Collectors.toList());
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