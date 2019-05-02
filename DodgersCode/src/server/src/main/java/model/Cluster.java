package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cluster {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    private int population;
    private double[] demographicPercentages = new double[DemographicType.values().length];

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
        edgeSet = p.neighborEdges;
        population = p.population;
    }

    public Cluster(Cluster c) {
        precinctSet = c.getPrecinctSet();
        edgeSet = c.getEdgeSet();
        population = c.getPopulation();
        demographicPercentages = c.getDemographicPercentages();
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

    public double[] getDemographicPercentages() {
        return demographicPercentages;
    }

    public void absorbCluster(Cluster c) {
        precinctSet.addAll(c.getPrecinctSet());
        edgeSet.addAll(c.getEdgeSet());

        final int absorbedPopulation = c.getPopulation();
        final int total = population + absorbedPopulation;

        //set demographic percentages to combined value
        for(int ii = 0; ii < demographicPercentages.length; ii++) {
            demographicPercentages[ii] = (demographicPercentages[ii] * (double)(population/total)) +
                                         (c.getDemographicPercentages()[ii] * (double)(absorbedPopulation/total));
        }
    }

    /**
     * a certain minority has a majority vote in the district
     * @return
     */
    public boolean isMajorityMinorityDistrict() {
        return Arrays.stream(demographicPercentages).noneMatch(dp -> dp > 0.5);//TODO this is wrong
    }
}

