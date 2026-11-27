package com.moein.plantcare.repository.mongo;

import static org.junit.Assert.assertEquals;

import java.net.InetSocketAddress;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class PlantMongoRepositoryTest {

	private static final String DB_NAME = "plantcare-test";
	private static final String COLLECTION_NAME = "plant";

	private static MongoServer server;
	private static InetSocketAddress serverAddress;

	private MongoClient client;
	private PlantMongoRepository plantRepository;

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
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void testFindAllWhenDatabaseIsEmpty() {
		assertEquals(Collections.emptyList(), plantRepository.findAll());
	}
}