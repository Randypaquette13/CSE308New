package model;

import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.util.HashSet;
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
        return mp.getEnvelope().getArea();
    }

    public double wastedDemVotes() {
        double diff = getDemographics().demVotes - getDemographics().repVotes;
        return diff < 0 ? diff : 0;
    }

    public double wastedRepVotes() {
        double diff = getDemographics().repVotes - getDemographics().demVotes;
        return diff < 0 ? diff : 0;
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
            if(neighbor.getDistrict().id == p.getDistrict().id) {
                precincts.add(neighbor);
            }
        }

        //for all precincts, each precinct must touch all other precincts
//        return precincts.stream().allMatch(precinct -> precincts.stream().anyMatch(precinct1 -> precinct.hasEdgeTo(precinct1)));
        return precincts.stream().allMatch(precinct -> precincts.stream().allMatch(precinct1 -> {
            if(precinct.getId() == precinct1.getId()) {
                return true;
            } else {
                return precinct.hasPathTo(precinct1, new LinkedList<>());
            }
        }));
        //every precinct must have a path to every member
//        return precincts.stream().allMatch(precinct -> getPrecinctSet().stream().allMatch(member -> {
//                if(member.getId() == precinct.getId()) {
//                    return true;
//                } else {
//                    return precinct.hasPathTo(member, new LinkedList<>());
//                }
//            }));
    }
//    return precincts.stream().allMatch(precinct -> precinct.getNeighborIDs(new LinkedList<>()).size() == getPrecinctSet().size());


    public HashSet<Precinct> calculateBorderPrecincts(){
        HashSet<Precinct> borderPrecincts = new HashSet<Precinct>();
        System.out.println("Precinct set: " + getPrecinctSet());
        for(Precinct currentPrecinct : getPrecinctSet()){
            for(Edge e : currentPrecinct.getEdges()){
                Precinct neighbor = (Precinct)e.getNeighbor(currentPrecinct);
                if(!neighbor.getDistrict().equals(currentPrecinct.getDistrict())){
                    borderPrecincts.add(currentPrecinct);
                }
            }
        }
        //System.out.println("Border Precincts: " + borderPrecincts);
        return borderPrecincts;
    }
}
