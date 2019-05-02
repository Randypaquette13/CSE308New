package model;

import java.util.Set;

public class Precinct extends Cluster {
    private final long id;
    private final int population;
    private final Set<Edge> neighborEdges;
    private District district;

    public Precinct(long id, int population, Set<Edge> neighborEdges) {
        super(new Precinct(id,population,neighborEdges));//TODO
        this.id = id;
        this.population = population;
        this.neighborEdges = neighborEdges;
    }

    public long getId() {
        return id;
    }

    public int getPopulation() {
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

    public double getArea() {
        return -1.0;//TODO
    }
}
