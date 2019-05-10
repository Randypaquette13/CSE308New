package model;

import java.util.*;

public class Cluster implements MapVertex {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    public int population;
    private Demographics demographics;
    public long id;

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
        edgeSet = p.getEdges();
        population = p.getPopulation();
        demographics = p.getDemographics();
        id = p.getId();
    }

    public Cluster(Cluster c) {
        precinctSet = c.getPrecinctSet();
        edgeSet = c.getEdges();
        population = c.getPopulation();
        demographics = c.getDemographics();
        id = c.id;
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

    @Override
    public Demographics getDemographics() {
        return demographics;
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

        //set demographics to combined value
        demographics.add(c.getDemographics());
    }

    /**
     * a certain minority has a majority vote in the district
     * @return
     */
    public boolean isMajorityMinorityDistrict() {
//        return Arrays.stream(demographicValues).noneMatch(dp -> dp > 0.5);//TODO this is wrong
        return false;
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

        sb.append("Cluster#" + id + " ");
        for(Precinct p : precinctSet) {
            sb.append(p);
            sb.append(",");
        }

        return sb.toString();
    }
}

