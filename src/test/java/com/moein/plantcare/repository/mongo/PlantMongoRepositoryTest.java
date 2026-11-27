package com.moein.plantcare.repository.mongo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.moein.plantcare.model.Plant;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class PlantMongoRepositoryTest {

	private static final String DB_NAME = "plantcare-test";
	private static final String COLLECTION_NAME = "plant";

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	private MongoClient client;
	private PlantMongoRepository plantRepository;
	private MongoCollection<Document> plantCollection;

	@BeforeClass
	public static void setupServer() {
		server = new MongoServer(new MemoryBackend());
		serverAddress = server.bind();
	}

	@AfterClass
	public static void shutdownServer() {
		server.shutdown();
	}

	@Before
	public void setup() {
		client = MongoClients.create("mongodb://localhost:" + serverAddress.getPort());

		client.getDatabase(DB_NAME).drop();

		plantRepository = new PlantMongoRepository(client, DB_NAME, COLLECTION_NAME);

		plantCollection = client.getDatabase(DB_NAME).getCollection(COLLECTION_NAME);
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void testFindAllWhenDatabaseIsEmpty() {
		assertEquals(Collections.emptyList(), plantRepository.findAll());
	}

	@Test
	public void testFindAllWhenDatabaseIsNotEmpty() {
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
	public void testFindByIdNotFound() {
		assertNull(plantRepository.findById("P001"));
	}

	@Test
	public void testFindByIdFound() {
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

	@Test
	public void testSave() {
		Plant plant = new Plant("P001", "Monstera", "Living Room", 7, LocalDate.of(2000, 1, 3));

		plantRepository.save(plant);

		assertEquals(1L, plantCollection.countDocuments());

		Document document = plantCollection.find().first();

		assertNotNull(document);
		assertEquals("P001", document.getString("id"));
		assertEquals("Monstera", document.getString("name"));
		assertEquals("Living Room", document.getString("location"));
		assertEquals(Integer.valueOf(7), document.getInteger("wateringIntervalDays"));
		assertEquals("2000-01-03", document.getString("lastWateredDate"));
	}

	@Test
	public void testDelete() {
		addTestPlantToDatabase("P001", "Monstera", "Living Room", 7, LocalDate.of(2000, 1, 3));

		addTestPlantToDatabase("P002", "Cactus", "Bedroom", 14, LocalDate.of(2000, 1, 4));

		plantRepository.delete("P001");

		assertEquals(1L, plantCollection.countDocuments());

		assertNull(plantCollection.find(Filters.eq("id", "P001")).first());

		assertNotNull(plantCollection.find(Filters.eq("id", "P002")).first());
	}

	private void addTestPlantToDatabase(String id, String name, String location, int wateringIntervalDays,
			LocalDate lastWateredDate) {

		plantCollection.insertOne(new Document().append("id", id).append("name", name).append("location", location)
				.append("wateringIntervalDays", wateringIntervalDays)
				.append("lastWateredDate", lastWateredDate.toString()));
	}
}