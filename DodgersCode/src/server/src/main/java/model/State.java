package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Configuration;
import controller.Move;
import org.locationtech.jts.geom.*;

import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The state objects will be instantiated when the server first starts
 */

public class State {
    private Set<District> districtSet;
    private Set<Precinct> precinctSet;
    private Collection<Cluster> clusters;
    private Move recentMove;
    private int population = 0;
    public boolean isGPDone;

    public State(Set<Precinct> precinctSet) {
        this.districtSet = new HashSet<>();
        this.precinctSet = precinctSet;
        this.clusters = new LinkedList<>();
        precinctSet.forEach(precinct -> {
            clusters.add(new Cluster(precinct));
            population+= precinct.getPopulation();
            isGPDone = false;
        });
    }

    public Set<District> getDistrictSet() {
        return districtSet;
    }

    public Set<Precinct> getPrecinctSet() {
        return precinctSet;
    }

    public Collection<Cluster> getClusters() {
        return clusters;
    }

    public int getPopulation() {
        return population;
    }

    public void setClusters(Collection<Cluster> newClusters){
        clusters = newClusters;
    }

    /**
     * Used to reset the state before every call to Algorithm.doJob() in AlgorithmController
     */
    public void reset() {
        districtSet = new HashSet<>();
        //precinctSet stays the same
        clusters = new LinkedList<>();
        precinctSet.forEach(precinct -> clusters.add(new Cluster(precinct)));
        isGPDone = false;
    }

    /**
     * Combines two clusters for graph partitioning. The first cluster absorbs the second and both the
     * first and second cluster is removed. Returns the combined cluster.
     */
    public Cluster combinePair(Cluster c1, Cluster c2) {
        c1.absorbCluster(c2);
        clusters.remove(c2);
        clusters.remove(c1);
        return c1;
    }

    /**
     * Activates the move. A 'move' moves a precinct from one district to another.
     */
    public void doMove(Move m) {
        recentMove = m;
        m.getFrom().removePrecinct(m.getPrecinct());
        m.getTo().addPrecinct(m.getPrecinct());
        m.getPrecinct().setDistrict(m.getTo());
    }

    /**
     * If the output of the objective function after the move is not satisfactory then call this method
     */
    public void undoMove() {
        if(recentMove == null) {
            return;
        }
        recentMove.getTo().removePrecinct(recentMove.getPrecinct());
        recentMove.getFrom().addPrecinct(recentMove.getPrecinct());

        recentMove.getPrecinct().setDistrict(recentMove.getFrom());
        recentMove = null;
    }

    /**
     * Finds a random candidate move for simulated annealing. May return null if there is no valid move see
     * @return a move with a high joinability
     */
    public Move findCandidateMove() {
        //get a random district
        final Random r = new Random();
        int index = r.nextInt(districtSet.size());

        Iterator<District> iter = districtSet.iterator();
        for(int i = 0; i < index; i++) {
            iter.next();
        }
        final District district = iter.next();

        Iterator<Precinct> piter = district.getPrecinctSet().iterator();
        index = r.nextInt(district.getPrecinctSet().size());
        for(int i = 0; i < index; i++) {
            piter.next();
        }
        while(piter.hasNext()) {
            Precinct precinct = piter.next();
//            System.out.println("precinct to move" + precinct);
            if (precinct.getDistrict().continuity(precinct)) {
//                System.out.println("continuitygood");

                for (Edge edge : precinct.getEdges()) {
                    final Precinct neighbor = (Precinct) edge.getNeighbor(precinct);
                    if (edge.getJoinability() > Configuration.ANNEALING_JOINABILITY_THRESHOLD && !neighbor.getDistrict().equals(district)) {
//                        System.out.println("move this:" + precinct);
    //                    System.out.println("from this:" + precinct.getDistrict());
    //                    System.out.println("to this:" + neighbor.getDistrict());
    //                    System.out.println("joinability: " + edge.getJoinability());
                        return new Move(district, neighbor.getDistrict(), precinct);
                    }
                }
            } else {
//                System.out.println("continuity bad");
            }
        }
//        findCandidateMove()
        return null;
    }

    /**
     * Get a new candidate pair for graph partitioning. Returns null if no candidate pair is found
     */
    public ClusterPair findCandidateClusterPair(int targetPop) {
        LinkedList<ClusterPair> pairs = new LinkedList<>();
        Cluster niceCluster = null;
        Edge niceEdge = null;
        int bestScore = Integer.MAX_VALUE;
//        System.out.println("TRYING TO FIND CANDIDATE CLUSTER PAIR");
        System.out.println("num clusters:" + getClusters().size());
        for(Cluster c : getClusters()) {
//            System.out.println("num edges:" + c.getEdges().size());
//            System.out.println("there are clusters");
            HashSet<Edge> edges = new HashSet<>(c.getEdges());
            for(Edge e : edges) {
//                System.out.println(targetPop);
//                System.out.println("the cluster has an edge");
//                System.out.println(getClusters().contains(e.getNeighbor(c)));
//                System.out.println(e.getNeighbor(c));
//                System.out.println(getClusters());
                if(getClusters().contains((Cluster) e.getNeighbor(c))) {//this is never true TODO
//                    System.out.println("Neighbor can be combined with");
//                    System.out.println(c);
//                    System.out.println("calc join of this cluster");


                    int popScore = Math.abs(targetPop - (c.getPopulation() + ((Cluster)e.getNeighbor(c)).getPopulation()));
                    if(popScore < bestScore) {
                        bestScore = popScore;
                        niceCluster = c;
                        niceEdge = e;

                        pairs.add(new ClusterPair(niceCluster, (Cluster)niceEdge.getNeighbor(niceCluster)));
                    }
                }
            }
        }

//        if(bestScore > (targetPop * targetPop/10)) {
////            System.out.println(targetPop);
//            return null;
//        }
//        System.out.println("num candidate pairs" +pairs.size());
        if(niceCluster != null && niceEdge != null) {
            ClusterPair bestPair = null;
            double maxJoin = 0;
            for(ClusterPair cp : pairs) {
                double join = -1;
                for (Edge e : cp.getC1().getEdges()){
                    if(e.getNeighbor(cp.getC1()).equals(cp.getC2())) {
                        join = e.getJoinability();
                    }
                }
                if(join == -1) {
//                    System.out.println("MASSIVE EDGE JOINABILITY ERROR");
                }
//                    System.out.println(join);
                if(join > maxJoin) {
                    maxJoin = join;
                    bestPair = cp;
                }
            }
            if(bestPair != null) {
                return bestPair;
            } else {
//                System.out.println("should never get here");
//                System.out.println(bestScore);
                return new ClusterPair(niceCluster, (Cluster)niceEdge.getNeighbor(niceCluster));
            }
        } else {
            return null;
        }
    }

    public void convertClustersToDistricts() {
        for(Cluster c : clusters) {
            District d = new District(c);
            districtSet.add(d);
            for(Precinct p : c.getPrecinctSet()) {
                p.setDistrict(d);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("S:");
        sb.append(" D: ");
        districtSet.forEach(sb::append);
        sb.append(" C: ");
        clusters.forEach(sb::append);
        sb.append(" P: ");
        precinctSet.forEach(e -> {
            sb.append(e);
            sb.append(",");
        });
        sb.append(" ");
        sb.append("Districts:" + districtSet.size() + " Clusters:" + clusters.size() + " Precincts:" + precinctSet.size());
        return sb.toString();
    }

    public String toFancyString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State:\n\t");
        districtSet.forEach(sb::append);
        sb.append("\n\t");
        clusters.forEach(sb::append);
        sb.append("\n\t");
        precinctSet.forEach(sb::append);
        sb.append("\n");

        sb.append("Districts:" + districtSet.size() + " Clusters:" + clusters.size() + " Precincts:" + precinctSet.size());
        sb.append("\n");
        return sb.toString();
    }

    public int numMaxMinDists() {
        int total = 0;
        for(District d : getDistrictSet()) {
            total += d.isMajorityMinorityDistrict() ? 1 : 0;
        }
        return total;
    }

    public static State getStateOLD(String stateName) {
        switch (stateName) {
            case "Arizona":
                break;
            case "New Hampshire":
                break;
            case "Maryland":
                break;
            default:
                break;
        }


        int id = 0;
        Precinct p0 = new Precinct(id++, 60, new HashSet<>(), Demographics.getDemographicTest(), "my county", getCoords(1, 2, 0, 1));
        Precinct p1 = new Precinct(id++, 60, new HashSet<>(), Demographics.getDemographicTest(), "my county", getCoords(2, 3, 0, 1));
        Precinct p2 = new Precinct(id++, 60, new HashSet<>(), Demographics.getDemographicTest(), "my county", getCoords(0, 1, 1, 2));
        Precinct p3 = new Precinct(id++, 60, new HashSet<>(), Demographics.getDemographicTest(), "my county", getCoords(0, 1, 0, 1));
        Precinct p4 = new Precinct(id++, 60, new HashSet<>(), Demographics.getDemographicTest(), "my county", getCoords(3, 4, 1, 2));
        Precinct p5 = new Precinct(id++, 60, new HashSet<>(), Demographics.getDemographicTest(), "my county", getCoords(3, 4, 0, 1));

        p0.addEdgeTo(p1);

        p0.addEdgeTo(p2);
        p0.addEdgeTo(p3);
        p2.addEdgeTo(p3);

        p1.addEdgeTo(p4);
        p1.addEdgeTo(p5);
        p4.addEdgeTo(p5);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p0);
        hsp.add(p1);
        hsp.add(p2);

        hsp.add(p3);
        hsp.add(p4);
        hsp.add(p5);

        State s = new State(hsp);
        s.reset();

        return s;
    }

    private static Coordinate[] getCoords(double xmin, double xmax, double ymin, double ymax) {
        Coordinate[] coordinates = {
                new Coordinate(xmin,ymin),
                new Coordinate(xmin,ymax),
                new Coordinate(xmax,ymax),
                new Coordinate(xmax,ymin),
                new Coordinate(xmin,ymin)};
        return coordinates;
    }


    public static State getState(String stateName) {
        return createStateFromFile(stateName);
    }

    public Collection<long[]> getClustersSimple() {
        Collection<long[]> out = new LinkedList<>();
        for(Cluster c : getClusters()) {
            long[] precincts = new long[c.getPrecinctSet().size()+1];
            ArrayList<Precinct> ps = new ArrayList<>(c.getPrecinctSet());
            precincts[0] = c.id;
            for(int ii = 1; ii < precincts.length; ii++) {
                precincts[ii] = ps.get(ii-1).getId();
            }
            out.add(precincts);
        }
        return out;
    }

    /**
     * Reads the precinct data and creates a state object based off of it.
     * @param state A String containing the state we want to load from a file.
     * @return A State object created from the precinct data.
     */
    public static State createStateFromFile(String state){
        String demVotesMarker = "";
        String repVotesMarker = "";
        String path = "";
        switch(state){
            case "Arizona":
                demVotesMarker = "PRS08_DEM";
                repVotesMarker = "PRS08_REP";
                path = "src/server/src/main/resources/static/ArizonaData/AZPrecinctData.json";
                break;
            case "New Hampshire":
                demVotesMarker = "PRES_DEM08";
                repVotesMarker = "PRES_REP08";
                path = "src/server/src/main/resources/static/NewHampshireData/NHPrecinctData.json";
                break;
            case "Maryland":
                path = "src/server/src/main/resources/static/MarylandData/MDPrecinctData.json";
                break;
        }
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper  = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData); //gets root of object
            JsonNode featuresNode = rootNode.path("features"); //gets array titled "features"
            Iterator<JsonNode> elements = featuresNode.elements();
            HashMap<Long, Precinct> precincts = new HashMap<Long, Precinct>();
            while(elements.hasNext()){
                JsonNode featureZero = elements.next();
                JsonNode coordinates = featureZero.path("geometry").path("coordinates");
                JsonNode propertiesNode = featureZero.path("properties"); //get the properties object from features.
                JsonNode polygonType = featureZero.path("geometry").path("type");
                ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>(); //will hold all the Coordinate objects
                if(polygonType.asText().equals("MultiPolygon")){

                    MultiPolygon m = handleMultiPolygon(coordinates);
                    Geometry g = m.convexHull();
                    coordinateList.addAll(Arrays.asList(g.getCoordinates()));
                    JsonNode precinctId = propertiesNode.path("OID_"); //precinct id
                }
                else{
                    Iterator<JsonNode> coordinatesAtZero = coordinates.elements(); //iterator to get the first element in coord array (only object)
                    if(coordinatesAtZero.hasNext()) {
                        JsonNode coordArray = coordinatesAtZero.next(); //coordinates is an array with one entry that holds arrays
                        Iterator<JsonNode> coordIterator = coordArray.elements();
                        while (coordIterator.hasNext()) {
                            JsonNode coordinate = coordIterator.next(); //get the next coordinate array.
                            //System.out.println("Coordinate at " + i++ + " is " + coordinate.toString());
                            Iterator<JsonNode> coordValue = coordinate.elements(); //to get the two values from the coordinate
                            double coord1 = coordValue.next().asDouble();
                            double coord2 = coordValue.next().asDouble();
                            Coordinate coord = new Coordinate(coord1, coord2); //read the two values into a Coordinate obj
                            //System.out.println("Coord is " + coord.toString());
                            coordinateList.add(coord);
                        }
                    }
                }

                //extract from the properties object
                JsonNode totalpop = propertiesNode.path("Total"); //total population
                JsonNode precinctId = propertiesNode.path("OID_"); //precinct id
                JsonNode voteDem = propertiesNode.path(demVotesMarker); //dem votes in 08 election
                JsonNode voteRep = propertiesNode.path(repVotesMarker); //rep votes in 08 election
                JsonNode hispanicPop = propertiesNode.path("Hispanic/Latino"); // hispanic population
                JsonNode asianPop = propertiesNode.path("Asian"); // asian population
                JsonNode afAmerPop = propertiesNode.path("Black"); // african american population
                JsonNode whitePop = propertiesNode.path("White"); // white population
                JsonNode nativePop = propertiesNode.path("AI/AN"); //american indians population
                JsonNode county = propertiesNode.path("COUNTY"); //county precinct is in.

                //population of demographics for Demographics object
                HashMap<DemographicType, Integer> demographicPop = new HashMap<DemographicType, Integer>();
                for(DemographicType t : DemographicType.values()){
                    switch (t){
                        case HISPANIC:
                            demographicPop.put(DemographicType.HISPANIC, hispanicPop.asInt());
                            break;
                        case ASIAN:
                            demographicPop.put(DemographicType.ASIAN, asianPop.asInt());
                            break;
                        case AFRICAN_AMERICAN:
                            demographicPop.put(DemographicType.AFRICAN_AMERICAN, afAmerPop.asInt());
                            break;
                        case WHITE:
                            demographicPop.put(DemographicType.WHITE, whitePop.asInt());
                            break;
                        case NATIVE:
                            demographicPop.put(DemographicType.NATIVE, nativePop.asInt());
                            break;
                        case OTHER:
                            demographicPop.put(DemographicType.OTHER, 0); //no other population
                            break;
                    }
                }

                Demographics d = new Demographics(demographicPop, voteDem.asDouble(), voteRep.asDouble());
                Precinct p = new Precinct(precinctId.asLong(), totalpop.asInt(), new HashSet<Edge>(), d,
                        county.toString(), coordinateList.toArray(new Coordinate[0]));
                precincts.put(p.getId(), p);

            }
            //now loop once again to create edges
            Iterator<JsonNode> featuresIterator = featuresNode.elements();
            while(featuresIterator.hasNext()){ //change to while
                JsonNode features = featuresIterator.next();
                JsonNode properties = features.path("properties");
                JsonNode neighbors = properties.path("neighbors");
                Long precinctId = properties.path("OID_").asLong();
                Precinct currentPrecinct = precincts.get(precinctId);
//                System.out.println(currentPrecinct + " has neighbors " + neighbors.toString());
//                System.out.println(precinctId);
                Iterator<JsonNode> neighborsIterator = neighbors.elements();
                while(neighborsIterator.hasNext()){
                    Long neighborId = neighborsIterator.next().asLong();
                    Precinct neighborPrecinct = precincts.get(neighborId);
                    currentPrecinct.addEdgeTo(neighborPrecinct);
                }
            }



            HashSet<Precinct> precinctSet = new HashSet<Precinct>();
            precinctSet.addAll(precincts.values());
            //System.out.println("Following precincts added: ");
            //for(Precinct p : precinctSet){
            //    System.out.println(" " + p + " has " + p.getEdges().size() + " edges.");
            //}
            //System.out.println(errorPrecincts.toString());
            return new State(precinctSet);
            //System.out.println("State: " + s);
        }
        catch(IOException e){
            System.out.println("could not read");
            return null;
        }

    }

    /**
     * Handles the case of the precinct data containing a multipolygon
     * @param coordinates The JsonNode which is the start of the coordinates array in the precinct data.
     * @return A MultiPolygon which represents the precinct.
     */
    private static MultiPolygon handleMultiPolygon(JsonNode coordinates){
        GeometryFactory geoFactory = new GeometryFactory();
        ArrayList<Polygon> polygons = new ArrayList<Polygon>();
        Iterator<JsonNode> coordinateIterator = coordinates.elements();
        while(coordinateIterator.hasNext()){
            JsonNode polygonNode = coordinateIterator.next();
            Iterator<JsonNode> polygonIterator = polygonNode.elements();
            if(polygonIterator.hasNext()){
                ArrayList<Coordinate> polygonCoordinates = new ArrayList<Coordinate>();
                JsonNode polygonCoordinate = polygonIterator.next();
                Iterator<JsonNode> polygonCoordinateIterator = polygonCoordinate.elements();
                while(polygonCoordinateIterator.hasNext()){
                    JsonNode points = polygonCoordinateIterator.next();
                    Iterator<JsonNode> pointsIterator = points.elements();
                    double point1 = pointsIterator.next().asDouble();
                    double point2 = pointsIterator.next().asDouble();
                    Coordinate c = new Coordinate(point1, point2);
                    polygonCoordinates.add(c);
                }
                Polygon p = geoFactory.createPolygon(polygonCoordinates.toArray(new Coordinate[0]));
                polygons.add(p);
            }
        }
        return geoFactory.createMultiPolygon(polygons.toArray(new Polygon[0]));
    }
}