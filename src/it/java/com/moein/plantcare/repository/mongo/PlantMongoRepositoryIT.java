package com.moein.plantcare.repository.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MongoDBContainer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.moein.plantcare.model.Plant;

public class PlantMongoRepositoryIT {

	private static final String DB_NAME = "plantcare-test";
	private static final String COLLECTION_NAME = "plant";

	@ClassRule
	public static final MongoDBContainer mongo = new MongoDBContainer("mongo:4.4.3");

	private MongoClient client;
	private PlantMongoRepository plantRepository;
	private MongoCollection<Document> plantCollection;

	@Before
	public void setup() {
		client = MongoClients.create(mongo.getReplicaSetUrl());

		client.getDatabase(DB_NAME).drop();

		plantRepository = new PlantMongoRepository(client, DB_NAME, COLLECTION_NAME);

		plantCollection = client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void testFindAll() {
		addTestPlantToDatabase("P001", "Monstera", "Living Room", 7, LocalDate.of(2000, 1, 3));

		addTestPlantToDatabase("P002", "Cactus", "Bedroom", 14, LocalDate.of(2000, 1, 4));

		List<Plant> plants = plantRepository.findAll();

		assertEquals(2, plants.size());

		Plant first = plants.stream().filter(plant -> "P001".equals(plant.getId())).findFirst().orElseThrow();

		Plant second = plants.stream().filter(plant -> "P002".equals(plant.getId())).findFirst().orElseThrow();

		assertEquals("Monstera", first.getName());
		assertEquals("Living Room", first.getLocation());
		assertEquals(7, first.getWateringIntervalDays());
		assertEquals(LocalDate.of(2000, 1, 3), first.getLastWateredDate());

		assertEquals("Cactus", second.getName());
		assertEquals("Bedroom", second.getLocation());
		assertEquals(14, second.getWateringIntervalDays());
		assertEquals(LocalDate.of(2000, 1, 4), second.getLastWateredDate());
	}

	@Test
	public void testFindById() {
		addTestPlantToDatabase("P001", "Monstera", "Living Room", 7, LocalDate.of(2000, 1, 3));

		addTestPlantToDatabase("P002", "Cactus", "Bedroom", 14, LocalDate.of(2000, 1, 4));

		Plant plant = plantRepository.findById("P002");

		assertNotNull(plant);
		assertEquals("P002", plant.getId());
		assertEquals("Cactus", plant.getName());
		assertEquals("Bedroom", plant.getLocation());
		assertEquals(14, plant.getWateringIntervalDays());
		assertEquals(LocalDate.of(2000, 1, 4), plant.getLastWateredDate());
	}

	private void addTestPlantToDatabase(String id, String name, String location, int wateringIntervalDays,
			LocalDate lastWateredDate) {

		plantCollection.insertOne(new Document().append("id", id).append("name", name).append("location", location)
				.append("wateringIntervalDays", wateringIntervalDays)
				.append("lastWateredDate", lastWateredDate.toString()));
	}
}