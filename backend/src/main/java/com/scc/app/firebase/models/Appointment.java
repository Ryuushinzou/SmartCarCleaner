package com.scc.app.firebase.models;

import java.sql.Date;
import java.util.List;

public class Appointment {
	private String id;
	private String vehicleId;
	private String locationId;
	private Date date;
	private Double price;
	private List acquiredOptions;
	private List requiredResources;

	public Appointment() {
	}

	public Appointment(String id, String vehicleId, String locationId, Date date, Double price, List acquiredOptions, List requiredResources) {
		this.id = id;
		this.vehicleId = vehicleId;
		this.locationId = locationId;
		this.date = date;
		this.price = price;
		this.acquiredOptions = acquiredOptions;
		this.requiredResources = requiredResources;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List getAcquiredOptions() {
		return acquiredOptions;
	}

	public void setAcquiredOptions(List acquiredOptions) {
		this.acquiredOptions = acquiredOptions;
	}

	public List getRequiredResources() {
		return requiredResources;
	}

	public void setRequiredResources(List requiredResources) {
		this.requiredResources = requiredResources;
	}

	public enum  Status {
		CREATED,
		IN_PROGRESS,
		PAID,
		NOT_PAID,
		DONE
	}
}
