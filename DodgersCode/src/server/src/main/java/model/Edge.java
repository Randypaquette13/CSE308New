package model;

public class Edge {

    private MapVertex c1;
    private MapVertex c2;
    private double joinability = -1;

    public Edge(MapVertex c1, MapVertex c2, double joinability) {
        this.c1 = c1;
        this.c2 = c2;
        this.joinability = joinability;
    }
    public MapVertex getC1() {
        return c1;
    }

    public MapVertex getC2() {
        return c2;
    }
    /**
     * Joinability is calculated based on the difference in the demographic values of the two clusters
     */
    public double getJoinability() {
        if(joinability != -1) {
            return joinability;
        }
        double totalDemographicDifference = 0;
        for(int ii = 0; ii < DemographicType.values().length; ii++) {
            totalDemographicDifference += Math.abs(c1.getDemographicValues()[ii] - c2.getDemographicValues()[ii]);
        }
        joinability = 1.0 / totalDemographicDifference;

        return joinability;
    }

    public MapVertex getNeighbor(MapVertex mv) {
        if(mv.equals(c1)) {
            return c2;
        } else {
            return c1;
        }
    }
}
