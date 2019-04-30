package model;

import controller.Configuration;
import controller.Move;

import java.util.*;

/**
 * The state objects will be instantiated when the server first starts
 */

public class State {
    Set<District> districtSet;
    Set<Precinct> precinctSet;
    Collection<Cluster>  clusters;
    int targetNumMaxMinDistricts;
    Move recentMove;

    public State(Set<District> districtSet, Set<Precinct> precinctSet, Collection<Cluster> clusters, int targetNumMaxMinDistricts) {
        this.districtSet = districtSet;
        this.precinctSet = precinctSet;
        this.clusters = clusters;
        this.targetNumMaxMinDistricts = targetNumMaxMinDistricts;
    }

    public Set<District> getDistrictSet() {
        return districtSet;
    }

    public Set<Precinct> getPrecinctSet() {
        return precinctSet;
    }

    public Collection<Cluster> getClusters() {
        return clusters;
    }

    public int getTargetNumMaxMinDistricts() {
        return targetNumMaxMinDistricts;
    }

    /**
     * Used to reset the state before every call to Algorithm.doJob() in AlgorithmController
     * @param p the preference used to set the numMaxMinDistricts
     */
    public void reset(Preference p) {
        districtSet = new HashSet<>();
        //precinctSet stays the same
        clusters = new LinkedList<>();//TODO which datatype to use
        precinctSet.forEach(precinct -> clusters.add(new Cluster(precinct)));
        targetNumMaxMinDistricts = p.getNumMaxMinDistricts();
    }

    /**
     * Combines two clusters for graph partitioning
     * @param c1
     * @param c2
     */
    public void combinePair(Cluster c1, Cluster c2) {//TODO is this all we need?
        clusters.remove(c2);
        c1.absorbCluster(c2);
    }

    /**
     * a 'move' moves a precinct from one district to another
     * @param m
     */
    public void doMove(Move m) {
        recentMove = m;
        m.getFrom().getPrecinctSet().remove(m.getPrecinct());
        m.getTo().getPrecinctSet().add(m.getPrecinct());
        //TODO finish?
    }
    public void undoMove() {
        if(recentMove == null) {
            return;
        }
        recentMove.getFrom().getPrecinctSet().add(recentMove.getPrecinct());
        recentMove.getTo().getPrecinctSet().add(recentMove.getPrecinct());
    }

    /**
     * Finds a random candidate move for simulated annealing. May return null if there is no valid move see TODO on the bottom
     * @return
     */
    public Move findCandidateMove() {
        Random r = new Random();
        int index = r.nextInt(districtSet.size()); //ew getting a random element from a set is O(n/2) thats garbage

        Iterator<District> iter = districtSet.iterator();
        for(int i = 0; i < index; i++) {
            iter.next();
        }
        District district = iter.next();

        for(Precinct precinct : district.getPrecinctSet()) {
            for(Edge edge : precinct.getNeighborEdges()) {
                if(edge.getJoinability() > Configuration.ANNEALING_JOINABILITY_THRESHOLD) {
                    Precinct neighbor = (Precinct)(edge.c1.equals(precinct) ? edge.c1 : edge.c2);

                    //if the neighbor is in another district
                    if(!neighbor.getDistrict().equals(district)) {
                        return new Move(district, neighbor.getDistrict(), precinct);
                    }
                }

            }
        }
//        findCandidateMove()
        return null;
    }

    /**
     * Get a new candidate pair for graph partitioning
     * @return
     */
    public ClusterPair findCandidatePair() {
        //TODO find candidate cluster pair for graph parittioning

        return new ClusterPair(null,null);//TODO output
    }
}