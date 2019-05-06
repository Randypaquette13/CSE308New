package model;

import java.util.Set;

public class Precinct implements MapVertex {
    private final long id;
    private final int population;
    private final Set<Edge> neighborEdges;
    private District district;
    private double[] demographicValues = new double[DemographicType.values().length];

    public Precinct(long id, int population, Set<Edge> neighborEdges, double[] demographicValues) {
        this.id = id;
        this.population = population;
        this.neighborEdges = neighborEdges;
        this.demographicValues = demographicValues;
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

    @Override
    public double[] getDemographicValues() {
        return demographicValues;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public double getArea() {
        return -1.0;//TODO
    }
}
