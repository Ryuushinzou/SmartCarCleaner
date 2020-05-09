package com.scc.app.mysql.repository;

import com.scc.app.model.Appointment;
import com.scc.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    List<Appointment> findByUserId(Long userId);

    List<Appointment> findByWashingStationId(Long washingStationId);
}
