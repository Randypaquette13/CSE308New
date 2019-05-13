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
        if(totalDemographicDifference == 0) {
            joinability = 1.0;
        } else {
            joinability = 1.0 / totalDemographicDifference;
        }
        joinability *= 0.5;

        if(c1 instanceof Precinct && c2 instanceof Precinct) {
            joinability *= ((Precinct) c1).getCounty().equals(((Precinct) c2).getCounty()) ? 2.0 : 1.0;
        }

        return joinability;
    }

    public MapVertex getNeighbor(MapVertex mv) {
        if(mv.equals(c1)) {
            return c2;
        } else {
            return c1;
        }
    }

    @Override
    public String toString() {
        return ("#1:" + c1 + " #2:" + c2 + " join:" + getJoinability());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Edge)) {
            return false;
        }
        if((getC1().equals(((Edge) obj).c1) || getC2().equals(((Edge) obj).c1)) && (getC1().equals(((Edge) obj).c2) || getC2().equals(((Edge) obj).c2))) {
            return true;
        } else return false;
    }

}
