package com.scc.app.service;

import com.scc.app.firebase.database.FbAppointmentsDatabase;
import com.scc.app.model.Appointment;
import com.scc.app.mysql.repository.AppointmentRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@Service
public class AppointmentService {

    @Autowired
    private FbAppointmentsDatabase fbAppointmentsDatabase;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private ConcurrentMap<Long, Appointment> idToAppointment = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, Appointment> idToAppointmentTemporary = new ConcurrentHashMap<>();

        if (Utils.isFirebaseDatabase()) {
            //TODO get all firebase
        } else {
            appointmentRepository.findAll().forEach(appointment -> idToAppointmentTemporary.put(appointment.getId(), appointment.clone()));
        }

        idToAppointment = idToAppointmentTemporary;
    }

    public Appointment saveAppointment(Appointment appointment) {

        if (Utils.isFirebaseDatabase()) {
            try {
                return fbAppointmentsDatabase.create(appointment);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            //TODO remove resources from washing station
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    public Appointment getAppointmentById(Long id) {
        return idToAppointment.get(id);
    }

    public Collection<Appointment> getAllAppointments() {
        return idToAppointment.values();
    }

    public Collection<Appointment> getAppointmentsForUser(Long userId) {

        return appointmentRepository.findByUserId(userId);
    }

    public Collection<Appointment> getAppointmentsByWashingStation(Long washingStationId) {

        return appointmentRepository.findByUserId(washingStationId);
    }

    public Appointment update(Appointment appointmentUpdated) {

        Appointment appointment = idToAppointment.get(appointmentUpdated.getId());
        if (appointment != null) {

            if (appointmentUpdated.getUserId() != null) {
                appointment.setUserId(appointmentUpdated.getUserId());
            }
            if (appointmentUpdated.getVehicleId() != null) {
                appointment.setVehicleId(appointmentUpdated.getVehicleId());
            }
            if (appointmentUpdated.getDateStart() != null) {
                appointment.setDateStart(appointmentUpdated.getDateStart());
            }
            if (appointmentUpdated.getDateEnd() != null) {
                appointment.setDateEnd(appointmentUpdated.getDateEnd());
            }

            if (appointmentUpdated.getPrice() != null) {
                appointment.setPrice(appointmentUpdated.getPrice());
            }
            if (appointmentUpdated.getWashingOptionsIds() != null) {
                appointment.setWashingOptionsIds(appointmentUpdated.getWashingOptionsIds());
            }

            if (appointmentUpdated.getAppointmentStatus() != null) {
                appointment.setAppointmentStatus(appointmentUpdated.getAppointmentStatus());
            }
            return appointmentRepository.save(appointment);
        }
        return null;
    }
}
