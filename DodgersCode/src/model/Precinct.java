package model;

import java.util.Set;

public class Precinct extends Cluster {
    long id;
    long population;
    Set<Edge> neighborEdges;
    District district;

    public Precinct(long id, long population, Set<Edge> neighborEdges) {
        super(new Precinct(id,population,neighborEdges));//TODO
        this.id = id;
        this.population = population;
        this.neighborEdges = neighborEdges;
    }

    public long getId() {
        return id;
    }

    public long getPopulation() {
        return population;
    }

    public Set<Edge> getNeighborEdges() {
        return neighborEdges;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
