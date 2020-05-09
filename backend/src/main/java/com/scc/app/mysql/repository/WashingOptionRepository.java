package com.scc.app.mysql.repository;

import com.scc.app.model.Supply;
import com.scc.app.model.WashingOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashingOptionRepository extends JpaRepository<WashingOption, Long> {

}
