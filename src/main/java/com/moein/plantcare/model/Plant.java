package com.moein.plantcare.model;

import java.time.LocalDate;

public class Plant {

	private final String id;
	private final String name;
	private final String location;
	private final int wateringIntervalDays;
	private final LocalDate lastWateredDate;

	public Plant(String id, String name, String location, int wateringIntervalDays, LocalDate lastWateredDate) {

		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("Plant ID must not be null or blank");
		}

		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Plant name must not be null or blank");
		}

		if (location == null || location.isBlank()) {
			throw new IllegalArgumentException("Plant location must not be null or blank");
		}

		if (wateringIntervalDays <= 0) {
			throw new IllegalArgumentException("Watering interval must be positive");
		}

		if (lastWateredDate == null) {
			throw new IllegalArgumentException("Last watered date must not be null");
		}

		if (lastWateredDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Last watered date must not be in the future");
		}

		this.id = id;
		this.name = name;
		this.location = location;
		this.wateringIntervalDays = wateringIntervalDays;
		this.lastWateredDate = lastWateredDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public int getWateringIntervalDays() {
		return wateringIntervalDays;
	}

	public LocalDate getLastWateredDate() {
		return lastWateredDate;
	}
}