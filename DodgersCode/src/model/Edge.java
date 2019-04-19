package model;

public class Edge {
    Cluster c1;
    Cluster c2;
    double joinability;

    public Edge(Cluster c1, Cluster c2, double joinability) {
        this.c1 = c1;
        this.c2 = c2;
        this.joinability = joinability;
    }
}
