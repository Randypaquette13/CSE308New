package cse308.server.repository;

import cse308.server.dao.JsonDistrictData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JsonDistrictRepository extends JpaRepository<JsonDistrictData, Long> {

    JsonDistrictData findByState(String state);
}
