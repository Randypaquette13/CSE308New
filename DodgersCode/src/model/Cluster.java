package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Cluster {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    long population;
    double[] demographicPercentages = new double[DemographicType.values().length];

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
        edgeSet = p.neighborEdges;
        population = p.population;
    }

    public Set<Precinct> getPrecinctSet() {
        return precinctSet;
    }

    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    public long getPopulation() {
        return population;
    }

    public double[] getDemographicPercentages() {
        return demographicPercentages;
    }

    public void absorbCluster(Cluster c) {//TODO every time you add a new member to Cluster you need to add it here
        precinctSet.addAll(c.getPrecinctSet());
        edgeSet.addAll(c.getEdgeSet());//TODO maybe do something special with edge

        long otherPopulation = c.getPopulation();
        long total = population + otherPopulation;

        //set demographic percentages to combined value
        for(int ii = 0; ii < demographicPercentages.length; ii++) {
            demographicPercentages[ii] = (demographicPercentages[ii] * (double)(population/total)) +
                                         (c.getDemographicPercentages()[ii] * (double)(otherPopulation/total));
        }
    }

    public boolean isMajorityMinorityDistrict() {
        return Arrays.stream(demographicPercentages).noneMatch(dp -> dp > 0.5);
    }
}

