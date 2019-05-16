package cse308.server.services;

import cse308.server.dao.JsonDistrictData;
import cse308.server.repository.JsonDistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonDistrictService {

    @Autowired
    JsonDistrictRepository districtRepository;

    public JsonDistrictData getDistrictData(String state){
        return districtRepository.findByState(state);
    }

    public boolean saveDistrictData(JsonDistrictData data){
        if (districtRepository.findByState(data.getState()) != null) {
            districtRepository.save(data);
            return true;
        }
        return false;
    }

}
