package cse308.server.dao;

import model.Precinct;

public class PrecinctAfricanDAO {
    long id;
    int africanPopulation;

    public PrecinctAfricanDAO(long id, int africanPopulation) {
        this.id = id;
        this.africanPopulation = africanPopulation;
    }

    protected PrecinctAfricanDAO() {

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAfricanPopulation() {
        return africanPopulation;
    }

    public void setAfricanPopulation(int africanPopulation) {
        this.africanPopulation = africanPopulation;
    }
}
