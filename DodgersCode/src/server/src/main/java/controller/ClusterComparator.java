package controller;

import model.Cluster;

import java.util.Comparator;

public class ClusterComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Cluster c1 = (Cluster)o1;
        Cluster c2 = (Cluster)o2;
        return c1.getPopulation() - c2.getPopulation();
    }
}
