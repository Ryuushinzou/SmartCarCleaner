package com.scc.app.service;

import com.scc.app.encryption.PasswordEncrypt;
import com.scc.app.firebase.database.FbUsersDatabase;
import com.scc.app.firebase.database.FbVehiclesDatabase;
import com.scc.app.model.User;
import com.scc.app.model.Vehicle;
import com.scc.app.mysql.repository.UserRepository;
import com.scc.app.mysql.repository.VehicleRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    private ConcurrentMap<Long, Vehicle> idToVehicle = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, Vehicle> idToAppointmentTemporary = new ConcurrentHashMap<>();

        vehicleRepository.findAll().forEach(vehicle -> idToAppointmentTemporary.put(vehicle.getId(), vehicle.clone()));

        idToVehicle = idToAppointmentTemporary;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {

        return vehicleRepository.save(vehicle);
    }

    public Vehicle deleteById(Long vehicleId) {

        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        vehicleRepository.deleteById(vehicleId);
        return vehicle.orElse(null);
    }

    public Vehicle getVehicleById(Long id) {
        return idToVehicle.get(id);
    }

    public Collection<Vehicle> getAllVehicles() {
        return idToVehicle.values();
    }

    public Vehicle update(Vehicle vehicleUpdated) {

        Vehicle vehicle = idToVehicle.get(vehicleUpdated.getId());
        if (vehicle != null) {

            if (vehicleUpdated.getMake() != null) {
                vehicle.setMake(vehicleUpdated.getMake());
            }
            if (vehicleUpdated.getModel() != null) {
                vehicle.setModel(vehicleUpdated.getModel());
            }
            if (vehicleUpdated.getType() != null) {
                vehicle.setType(vehicleUpdated.getType());
            }

            if (vehicleUpdated.getYear() != null) {
                vehicle.setYear(vehicleUpdated.getYear());
            }
        }
        return null;
    }
}
