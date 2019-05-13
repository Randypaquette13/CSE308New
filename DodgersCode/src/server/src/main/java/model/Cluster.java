package model;

import java.util.*;

public class Cluster implements MapVertex {
    private Set<Precinct> precinctSet;
    private Set<Edge> edgeSet;
    public int population;
    private Demographics demographics;
    public boolean isMajMinDist;
    public long id;

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
//        edgeSet = p.getEdges();
        population = p.getPopulation();
        demographics = p.getDemographics();
        id = p.getId();

        p.parentCluster = this;
        edgeSet = new HashSet<>();
        for(Edge e : p.getEdges()) {
            if(e.getNeighbor(p) instanceof Precinct) {
                if(((Precinct) e.getNeighbor(p)).parentCluster != null) {
                    addEdgeTo(((Precinct) e.getNeighbor(p)).parentCluster);
                }
            }
        }


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
     * a certain minority has a majority population in the district
     * @return
     */
    public boolean isMajorityMinorityDistrict() {
        HashMap<DemographicType, Integer> dPop = getDemographics().getDemographicPopulation();

        for(Map.Entry<DemographicType, Integer> e : dPop.entrySet()) {
            if(!e.getKey().equals(DemographicType.WHITE)) {
                if(e.getValue() > dPop.get(DemographicType.WHITE)) {
                    isMajMinDist = true;
                    return true;
                }
            }
        }
        isMajMinDist = false;
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
    public void addEdgeTo(MapVertex p) {
        Edge e1 = new Edge(this,p);
        getEdges().add(e1);
        p.getEdges().add(e1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("C" + id + " ");
        for(Precinct p : precinctSet) {
            sb.append(p);
            sb.append(",");
        }

        return sb.toString();
    }
}

