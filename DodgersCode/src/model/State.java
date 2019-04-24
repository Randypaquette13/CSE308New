package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * The state objects will be instantiated when the server first starts
 */

public class State {
    Set<District> districtSet;
    Set<Precinct> precinctSet;
    Collection<Cluster>  clusters;
    int targetNumMaxMinDistricts;

    public State(Set<District> districtSet, Set<Precinct> precinctSet, Collection<Cluster> clusters, int targetNumMaxMinDistricts) {
        this.districtSet = districtSet;
        this.precinctSet = precinctSet;
        this.clusters = clusters;
        this.targetNumMaxMinDistricts = targetNumMaxMinDistricts;
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

    public void combinePair(Cluster c1, Cluster c2) {
        clusters.remove(c2);
        c1.absorbCluster(c2);
    }
}
