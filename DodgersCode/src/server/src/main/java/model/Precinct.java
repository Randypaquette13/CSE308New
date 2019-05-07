package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Precinct implements MapVertex {
    private final long id;
    private final int population;
    private final Set<Edge> edgeSet;
    private District district;
    private Demographics demographics;
    private static long numPrecincts = 0;

    public Precinct(int population, Set<Edge> edgeSet, Demographics demographics) {
        this.id = numPrecincts++;
        this.population = population;
        this.edgeSet = edgeSet;
        this.demographics = demographics;
    }

    public long getId() {
        return id;
    }

    public int getPopulation() {
        return population;
    }

    public Set<Edge> getEdges() {
        return edgeSet;
    }

    public District getDistrict() {
        return district;
    }

    @Override
    public Demographics getDemographics() {
        return demographics;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public double getArea() {
        return -1.0;//TODO
    }

    @Override
    public List<MapVertex> getNeighbors() {
        LinkedList<MapVertex> neighbors = new LinkedList<>();
        for(Edge e : edgeSet) {
            neighbors.add(e.getNeighbor(this));
        }
        return neighbors;
    }

    @Override
    public String toString() {
        return "P" + id + " ";// + " population:" + population;
    }
}
