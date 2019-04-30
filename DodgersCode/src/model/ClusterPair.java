package model;

public class ClusterPair {
    Cluster c1;
    Cluster c2;

    public ClusterPair(Cluster c1, Cluster c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public Cluster getC1() {
        return c1;
    }

    public Cluster getC2() {
        return c2;
    }
}
