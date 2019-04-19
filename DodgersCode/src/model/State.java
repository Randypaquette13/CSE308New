package model;

import java.util.Collection;
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
}
