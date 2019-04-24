package model;

import java.util.HashSet;
import java.util.Set;

public class Cluster {
    Set<Precinct> precinctSet;

    public Cluster(Precinct p) {
        precinctSet = new HashSet<>();
        precinctSet.add(p);
    }
}
