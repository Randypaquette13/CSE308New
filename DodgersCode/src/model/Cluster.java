package model;

import java.util.HashSet;
import java.util.Set;

public class Cluster {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    long population;

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

    public void absorbCluster(Cluster c) {//TODO every time you add a new member to Cluster you need to add it here
        precinctSet.addAll(c.getPrecinctSet());
        edgeSet.addAll(c.getEdgeSet());
        population += population;

    }
}
