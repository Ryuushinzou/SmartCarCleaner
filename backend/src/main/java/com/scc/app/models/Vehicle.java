package com.scc.app.models;

public class Vehicle {
	private String id;
	private String make;
	private String model;
	private String type;
	private String year;

	public Vehicle() {
	}

	public Vehicle(String id, String make, String model, String type, String year) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.type = type;
		this.year = year;
	}

	public Vehicle(String make, String model, String type, String year) {
		this.make = make;
		this.model = model;
		this.type = type;
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
