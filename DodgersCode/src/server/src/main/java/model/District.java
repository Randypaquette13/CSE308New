package model;

import java.util.LinkedList;

public class District extends Cluster {

    public District(Cluster c) {
        super(c);
    }

    public double getPerimeter() {//TODO
        return 10;
    }

    public double getArea() {
        double area = 0;
        for(Precinct p : getPrecinctSet()) {
            area += p.getArea();
        }
        return area;
    }

    public void addPrecinct(Precinct p) {
        getPrecinctSet().add(p);
        population += p.getPopulation();
        getDemographics().add(p.getDemographics());
    }
    public void removePrecinct(Precinct p) {
        getPrecinctSet().remove(p);
        population -= p.getPopulation();
        getDemographics().remove(p.getDemographics());
    }
    public double getBoundingCircleArea() {//TODO
        return -1.0;
    }

    public double getConvexHull() {//TODO
        return -1.0;
    }

    public int wastedDemVotes() {
        int total = 0;

        for(DemographicType d : DemographicType.values()) {
            int dem = getDemographics().getDemographicVotingData().get(d)[0];
            int rep = getDemographics().getDemographicVotingData().get(d)[1];

            if(dem != Math.max(dem,rep)) {
                total += dem;
            }
        }
        return total;
    }

    public int wastedRepVotes() {
        int total = 0;
        for(DemographicType d : DemographicType.values()) {
//            System.out.println(this.getPopulation());
            int dem = getDemographics().getDemographicVotingData().get(d)[0];
//            System.out.println("\t" + dem);
            int rep = getDemographics().getDemographicVotingData().get(d)[1];
//            System.out.println("\t" + rep);
//            int oth = getDemographics().getDemographicVotingData().get(d)[2];
//            System.out.println("\t" + oth);

            if(rep != Math.max(dem,rep)) {
                total += rep;
            }
//            System.out.println("\ttotale:" + total);
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("D" + super.id + " ");
        for(Precinct p : getPrecinctSet()) {
            sb.append(p);
            sb.append(",");
        }

        return sb.toString();
    }

    public boolean continuity(Precinct p) {
        LinkedList<Precinct> precincts = new LinkedList<>();//precincts in this district that are adj to p
        for(Edge e : p.getEdges()) {
            final Precinct neighbor = (Precinct)e.getNeighbor(p);
            if(neighbor.getDistrict().equals(p.getDistrict())) {
                precincts.add(neighbor);
            }
        }

        for(Precinct precinct : precincts) {
            if(getEdges().stream().anyMatch(e -> {
                if(e.getNeighbor(precinct) instanceof Precinct) {
                    final Precinct neighbor = (Precinct) e.getNeighbor(precinct);
                    return !precincts.contains(neighbor);
                } else {
                    return false;
                }
            })) {
                return false;
            }
        }
        return true;

    }
}
