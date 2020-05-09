package com.scc.app.mysql.repository;

import com.scc.app.model.User;
import com.scc.app.model.WashingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashingStationRepository extends JpaRepository<WashingStation, Long> {

}
