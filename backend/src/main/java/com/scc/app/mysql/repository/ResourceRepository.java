package com.scc.app.mysql.repository;

import com.scc.app.model.Resource;
import com.scc.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
