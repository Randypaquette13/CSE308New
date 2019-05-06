package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cluster implements MapVertex {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    private int population;
    private double[] demographicValues = new double[DemographicType.values().length];

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
        edgeSet = p.getNeighborEdges();
        population = p.getPopulation();
        demographicValues = p.getDemographicValues();
    }

    public Cluster(Cluster c) {
        precinctSet = c.getPrecinctSet();
        edgeSet = c.getEdgeSet();
        population = c.getPopulation();
        demographicValues = c.getDemographicValues();
    }

    public Set<Precinct> getPrecinctSet() {
        return precinctSet;
    }

    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    public int getPopulation() {
        return population;
    }

    public double[] getDemographicValues() {
        return demographicValues;
    }

    public void absorbCluster(Cluster c) {
        precinctSet.addAll(c.getPrecinctSet());
        edgeSet.addAll(c.getEdgeSet());
        population += c.getPopulation();

        //set demographic percentages to combined value
        for(int ii = 0; ii < demographicValues.length; ii++) {
            demographicValues[ii] = (demographicValues[ii] + c.getDemographicValues()[ii]);
        }
    }

    /**
     * a certain minority has a majority vote in the district
     * @return
     */
    public boolean isMajorityMinorityDistrict() {
        return Arrays.stream(demographicValues).noneMatch(dp -> dp > 0.5);//TODO this is wrong
    }
}

