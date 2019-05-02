package model;

public class Edge {

    private Cluster c1;
    private Cluster c2;
    private double joinability = -1;

    public Edge(Cluster c1, Cluster c2, double joinability) {
        this.c1 = c1;
        this.c2 = c2;
        this.joinability = joinability;
    }
    public Cluster getC1() {
        return c1;
    }

    public Cluster getC2() {
        return c2;
    }
    /**
     * Joinability is calculated based on the difference in the demographic percentages of the two clusters
     */
    public double getJoinability() {
        if(joinability != -1) {
            return joinability;
        }
        double totalDemographicDifference = 0;
        for(int ii = 0; ii < DemographicType.values().length; ii++) {
            totalDemographicDifference += Math.abs(c1.getDemographicPercentages()[ii] - c2.getDemographicPercentages()[ii]);
        }
        joinability = 1.0 / totalDemographicDifference;

        return joinability;
    }
}
