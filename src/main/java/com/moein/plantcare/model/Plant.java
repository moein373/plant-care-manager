package com.moein.plantcare.model;

import java.time.LocalDate;

public class Plant {

	private final String id;
	private final String name;
	private final String location;
	private final int wateringIntervalDays;

	public Plant(String id, String name, String location, int wateringIntervalDays, LocalDate lastWateredDate) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.wateringIntervalDays = wateringIntervalDays;
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
		return null;
	}
}