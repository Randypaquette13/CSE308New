package model;

import controller.Configuration;
import controller.Move;

import java.util.*;

/**
 * The state objects will be instantiated when the server first starts
 */

public class State {
    private Set<District> districtSet;
    private Set<Precinct> precinctSet;
    private Collection<Cluster>  clusters;
    private int targetNumMaxMinDistricts;//TODO this should be a range
    private Move recentMove;

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
        clusters = new LinkedList<>();
        precinctSet.forEach(precinct -> clusters.add(new Cluster(precinct)));
        targetNumMaxMinDistricts = p.getNumMaxMinDistricts();
    }

    /**
     * Combines two clusters for graph partitioning. The first cluster absorbs the second and the
     * second cluster is removed.
     * @param c1
     * @param c2
     */
    public void combinePair(Cluster c1, Cluster c2) {
        clusters.remove(c2);
        c1.absorbCluster(c2);
    }

    /**
     * Activates the move. A 'move' moves a precinct from one district to another.
     * @param m
     */
    public void doMove(Move m) {
        recentMove = m;
        m.getFrom().getPrecinctSet().remove(m.getPrecinct());
        m.getTo().getPrecinctSet().add(m.getPrecinct());
        m.getPrecinct().setDistrict(m.getTo());
    }

    /**
     * If the output of the objective function after the move is not satisfactory then call this method
     */
    public void undoMove() {
        if(recentMove == null) {
            return;
        }
        recentMove.getFrom().getPrecinctSet().add(recentMove.getPrecinct());
        recentMove.getTo().getPrecinctSet().remove(recentMove.getPrecinct());
        recentMove.getPrecinct().setDistrict(recentMove.getFrom());
        recentMove = null;
    }

    /**
     * Finds a random candidate move for simulated annealing. May return null if there is no valid move see TODO on the bottom
     * @return a move with a high joinability
     */
    public Move findCandidateMove() {
        //get a random district
        Random r = new Random();
        int index = r.nextInt(districtSet.size());

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
     */
    public ClusterPair findCandidatePair() {
        //TODO find candidate cluster pair for graph parittioning
        return new ClusterPair(null,null);//TODO output
    }
}