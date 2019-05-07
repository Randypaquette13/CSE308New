package model;

import java.util.*;

public class Cluster implements MapVertex {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    private int population;
    private double[] demographicValues = new double[DemographicType.values().length];

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
        edgeSet = p.getEdges();
        population = p.getPopulation();
        demographicValues = p.getDemographicValues();
    }

    public Cluster(Cluster c) {
        precinctSet = c.getPrecinctSet();
        edgeSet = c.getEdges();
        population = c.getPopulation();
        demographicValues = c.getDemographicValues();
    }

    public Set<Precinct> getPrecinctSet() {
        return precinctSet;
    }

    public Set<Edge> getEdges() {
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
        for(Edge e : c.getEdges()) {
            if(!edgeSet.add(e)) {
                edgeSet.remove(e);
            }
        }
        population += c.getPopulation();

        getNeighbors().forEach(neighbor -> {
            if(c.getNeighbors().contains(neighbor)) {
                //TODO adjust the edge that contains this cluster
            }
        });


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
        StringBuilder sb = new StringBuilder();

        sb.append("Cluster: ");
        for(Precinct p : precinctSet) {
            sb.append(p);
            sb.append(",");
        }

        return sb.toString();
    }
}

