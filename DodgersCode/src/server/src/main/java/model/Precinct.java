package model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.toIntExact;

public class Precinct implements MapVertex {
    private final long id;
    private final int population;
    private final Set<Edge> edgeSet;
    private District district;
    private Demographics demographics;
    private static long numPrecincts = 0;
    private String county;
    public Cluster parentCluster = null;

    private Polygon polygon;

    public Precinct(long id, int population, Set<Edge> edgeSet, Demographics demographics, String county, Coordinate[] coordinates) {
        this.id = id;
        numPrecincts++;
        this.population = population;
        this.edgeSet = edgeSet;
        this.demographics = demographics;
        this.county = county;

        final GeometryFactory geometryFactory = new GeometryFactory();
        this.polygon = geometryFactory.createPolygon(coordinates);
    }

    public long getId() {
        return id;
    }

    public int getPopulation() {
        return population;
    }

    public Set<Edge> getEdges() {
        return edgeSet;
    }

    public District getDistrict() {
        return district;
    }

    @Override
    public Demographics getDemographics() {
        return demographics;
    }

    public String getCounty() {
        return county;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    @Override
    public List<MapVertex> getNeighbors() {
        LinkedList<MapVertex> neighbors = new LinkedList<>();
        for(Edge e : edgeSet) {
            neighbors.add(e.getNeighbor(this));
        }
        return neighbors;
    }
    public List<Long> getNeighborIDs(LinkedList<Long> ids) {
        for(Edge e : edgeSet) {
            Precinct p = ((Precinct)e.getNeighbor(this));
            if(p.getDistrict().id == this.getDistrict().id && !ids.contains(p.id)) {
                ids.add(p.id);
                ids.addAll(p.getNeighborIDs(ids));
            }
        }
        return ids;
    }

    public void addEdgeTo(MapVertex p) {
        Edge e1 = new Edge(this,p);
        getEdges().add(e1);
        p.getEdges().add(e1);
    }

    @Override
    public String toString() {
        return "P" + id;// + " population:" + population;
    }

    public boolean hasEdgeTo(Precinct p) {
        if(p.id == id) {
            return false;
        }
        return edgeSet.stream().anyMatch(edge -> ((Precinct)edge.getNeighbor(this)).getId() == p.getId());
    }

    public boolean hasPathTo(Precinct p, LinkedList<Long> placesYouBeen) {
        if(p.id == id) {
            return false;
        }
        return edgeSet.stream().anyMatch(edge -> {
            Precinct newP = ((Precinct)edge.getNeighbor(this));
            if(newP.getId() == p.getId()) {
                return true;
            }
            if(p.getDistrict().id == this.getDistrict().id) {
                if(!placesYouBeen.contains(newP.id)) {
                    placesYouBeen.add(newP.id);
                    return newP.hasPathTo(p,placesYouBeen);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        });
    }

//    @Override
//    public boolean equals(Object obj) {
//        if(!(obj instanceof Precinct)) {
//            return false;
//        } else {
//            return ((Precinct)obj).id == id;
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        return toIntExact(id);
//    }
}
