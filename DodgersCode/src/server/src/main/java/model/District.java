package model;

import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.util.Iterator;
import java.util.LinkedList;

public class District extends Cluster {

    public District(Cluster c) {
        super(c);
    }

    public MultiPolygon computeMulti() {
        Polygon[] polygons = new Polygon[getPrecinctSet().size()];

        Iterator<Precinct> piter = getPrecinctSet().iterator();
        for(int ii = 0; ii < polygons.length; ii++) {
            polygons[ii] = piter.next().getPolygon();
        }

        MultiPolygon mp = new MultiPolygon(polygons,new GeometryFactory());
        return mp;
    }

    public double getPerimeter() {
        MultiPolygon mp = computeMulti();
        return mp.convexHull().getLength();
    }

    public double getArea() {
        MultiPolygon mp = computeMulti();
        return mp.getArea();
    }

    public void addPrecinct(Precinct p) {
        getPrecinctSet().add(p);
        population += p.getPopulation();
        getDemographics().add(p.getDemographics());
    }
    public void removePrecinct(Precinct p) {
//        System.out.println("\tremoving P:" + p);
        getPrecinctSet().remove(p);
        population -= p.getPopulation();
        getDemographics().remove(p.getDemographics());
    }
    public double getBoundingCircleArea() {
        MultiPolygon mp = computeMulti();
        MinimumBoundingCircle mbc = new MinimumBoundingCircle(mp);
        return Math.PI * Math.pow(mbc.getRadius(),2);
    }

    public double getConvexHull() {
        MultiPolygon mp = computeMulti();
        return mp.convexHull().getArea();
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
