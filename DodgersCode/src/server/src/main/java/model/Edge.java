package model;

public class Edge {

    private MapVertex c1;
    private MapVertex c2;
    private double joinability = 0;

    public Edge(MapVertex c1, MapVertex c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
    public MapVertex getC1() {
        return c1;
    }

    public MapVertex getC2() {
        return c2;
    }
    /**
     * Joinability is calculated based on the difference in the demographic values of the two clusters
     * 0 means not joinable
     */
    public double getJoinability() {
        double totalDemographicDifference = 0;
        for(DemographicType demoType : DemographicType.values()) {
            //compare demographic populations
            totalDemographicDifference += Math.abs(c1.getDemographics().getDemographicPopulation().get(demoType) - c2.getDemographics().getDemographicPopulation().get(demoType));
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
