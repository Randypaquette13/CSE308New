import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Algorithm;
import controller.Move;
import cse308.server.dao.JsonDistrictData;
import cse308.server.dao.PreferenceDAO;
import model.*;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.junit.Test;
import org.locationtech.jts.geom.*;
//import org.locationtech.jts.geom.Polygon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

public class ModelTests {

    @Test
    public void combineClusterPairTest() {
//        System.out.println("COMBINE CLUSTER TEST");

        Demographics d1 = Demographics.getDemographicTest();

        int id = 0;
//        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(id++,10,new HashSet<>(), d1,"my county", null);
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(id++,11,new HashSet<>(), d1,"my county", null);

        Edge e1 = new Edge(p1,p2);
        p1.getEdges().add(e1);
        p2.getEdges().add(e1);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(hsp);

        System.out.println(s);
        ArrayList<Cluster> clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

        assertEquals(1, clusters.get(0).getPrecinctSet().size());
        assertEquals(1, clusters.get(1).getPrecinctSet().size());
        Cluster c = s.combinePair(clusters.get(0),clusters.get(1));
        System.out.println(s);
        System.out.println(c);

//        assertEquals( 1, s.getClusters().size());
//        Cluster c = s.getClusters().iterator().next();

        for(DemographicType type : DemographicType.values()) {
            assertEquals(20, c.getDemographics().getDemographicPopulation().get(type).intValue());
        }
        assertEquals(0, c.getEdges().size());
        assertEquals(21, c.getPopulation());
        assertEquals(2, c.getPrecinctSet().size());

//        System.out.println(c.getDemographics());

    }

    @Test
    public void testReset() {
//        System.out.println("TEST RESET");
        Demographics d1 = Demographics.getDemographicTest();

        int id = 0;
//        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(id++,10,new HashSet<>(), d1,"my county",null);
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(id++,11,new HashSet<>(), d1,"my county", null);

        Edge e1 = new Edge(p1,p2);
        p1.getEdges().add(e1);
        p2.getEdges().add(e1);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(hsp);

        ArrayList<Cluster> clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

//        System.out.println(s);
        s.combinePair(clusters.get(0),clusters.get(1));
//        System.out.println(s);
//        s.reset();
//        System.out.println(s);

        clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

//        System.out.println(clusters.get(0));
//        System.out.println(clusters.get(1));

        assertEquals(1, clusters.get(0).getPrecinctSet().size());
        assertEquals(1, clusters.get(1).getPrecinctSet().size());
    }

    @Test
    public void testDoMove() {
        Demographics d1 = Demographics.getDemographicTest();
        int id = 0;
        Precinct p0 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p1 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p2 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p3 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p4 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p5 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);

        p0.addEdgeTo(p1);

        p0.addEdgeTo(p2);
        p0.addEdgeTo(p3);

        p1.addEdgeTo(p4);
        p1.addEdgeTo(p5);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p0);
        hsp.add(p1);
        hsp.add(p2);

        hsp.add(p3);
        hsp.add(p4);
        hsp.add(p5);

        State s = new State(hsp);
        System.out.println(s);
        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        System.out.println("clusters before: "+clusters);
        Set<Cluster> newClusters = new HashSet<>();
        newClusters.add(s.combinePair(clusters.get(5),clusters.get(2)));
        newClusters.add(s.combinePair(clusters.get(5),clusters.get(3)));

        newClusters.add(s.combinePair(clusters.get(1),clusters.get(0)));
        newClusters.add(s.combinePair(clusters.get(1),clusters.get(4)));

        System.out.println("\nthis should be empty"+ s.getClusters());
        s.setClusters(newClusters);
        s.convertClustersToDistricts();
        assertEquals(2, s.getClusters().size());
        assertEquals(2, s.getDistrictSet().size());


        ArrayList<District> districts = new ArrayList<>(s.getDistrictSet());
        System.out.println(districts);
        assertEquals(30, districts.get(0).getPopulation());
        System.out.println("moving precinct: " + p1);
        s.doMove(new Move(districts.get(1),districts.get(0),p1));
        districts = new ArrayList<>(s.getDistrictSet());
        System.out.println(districts);

        assertEquals(40, districts.get(0).getPopulation());
        assertEquals(20, districts.get(1).getPopulation());

        System.out.println("moving back: " + p1);
        s.doMove(new Move(districts.get(0),districts.get(1),p1));
        districts = new ArrayList<>(s.getDistrictSet());
        System.out.println(districts);
        assertEquals(30, districts.get(0).getPopulation());

        System.out.println("moving precinct: " + p0);
        s.doMove(new Move(districts.get(0),districts.get(1),p0));
        districts = new ArrayList<>(s.getDistrictSet());
        System.out.println(districts);

        System.out.println("moving precinct: " + p2);
        Move mm = new Move(districts.get(0),districts.get(1),p2);
        s.doMove(mm);
        districts = new ArrayList<>(s.getDistrictSet());
        System.out.println(districts);
        System.out.println("undo precinct: " + p2);
        s.undoMove();
        districts = new ArrayList<>(s.getDistrictSet());
        System.out.println(districts);
    }

    @Test
    public void testSimulatedAnnealing() {
        State s = State.getState("sdljf");

        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        clusters.sort(Comparator.comparingLong(a -> a.id));
        Set<Cluster> newClusters = new HashSet<>();
        newClusters.add(s.combinePair(clusters.get(0),clusters.get(1)));
        newClusters.add(s.combinePair(clusters.get(0),clusters.get(2)));
        newClusters.add(s.combinePair(clusters.get(0),clusters.get(3)));

//        newClusters.add(s.combinePair(clusters.get(1),clusters.get(4)));
        newClusters.add(s.combinePair(clusters.get(4),clusters.get(5)));

        s.setClusters(newClusters);

//        double[] darr = new double[MeasureType.values().length];
//        Arrays.fill(darr, 1.0);
        HashMap<MeasureType,Double> weights = new HashMap<>();
        for(int i = 0; i < MeasureType.values().length;i++) {
            weights.put(MeasureType.values()[i],1.0);
        }

        Algorithm a = new Algorithm(new Preference(weights,2,0,2,true, "Arizona"),s);
        int i = 0;
        Summary sum = a.doSimulatedAnnealing();
        while (sum.getMove() != null) {
            i++;
            sum = a.doSimulatedAnnealing();
        } System.out.println(++i);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(sum.toString());
        System.out.println(s.getClusters());
    }

    @Test
    public void majorityMinorityTest(){
        int id = 0;
        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        p0.addEdgeTo(p1);
        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p0);
        hsp.add(p1);
        State s = new State(hsp);
        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        clusters.sort(Comparator.comparingLong(a -> a.id));
        System.out.println(clusters);
        System.out.println(clusters.get(0).isMajorityMinorityDistrict());
    }

    @Test
    public void testGraphPartitioning() {
        int id = 0;
        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p2 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p3 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p4 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p5 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);

        p0.addEdgeTo(p1);

        p0.addEdgeTo(p2);
        p0.addEdgeTo(p3);

        p1.addEdgeTo(p4);
        p1.addEdgeTo(p5);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p0);
        hsp.add(p1);
        hsp.add(p2);

        hsp.add(p3);
        hsp.add(p4);
        hsp.add(p5);

        State s = new State(hsp);
        System.out.println(s.toString());
        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        clusters.sort(Comparator.comparingLong(a -> a.id));
        System.out.println(clusters);

        s.getClusters().add(s.combinePair(clusters.get(0),clusters.get(1)));
        System.out.println("weird: " + s.getClusters());
        HashMap<MeasureType,Double> weights = new HashMap<>();
        for(int i = 0; i < MeasureType.values().length;i++) {
            weights.put(MeasureType.values()[i],1.0);
        }

        Algorithm a = new Algorithm(new Preference(weights,2,0,2,false, "Arizona"),s);
        System.out.println(a.doGraphPartitioning());
        System.out.println(s.getClusters());
        System.out.println(a.doGraphPartitioning());
        System.out.println(s.getClusters());
        System.out.println(a.doGraphPartitioning());
        System.out.println(s.getClusters());

//        System.out.println(a.doGraphPartitioning());
//        String gps = "";
//        while(!"done".equals(gps)) {
//            gps = a.doGraphPartitioning();
//            System.out.println(gps);
//        }
    }

    @Test
    public void testWholeFUCKINGThing() {
        int id = 0;
        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p2 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p3 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p4 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);
        Precinct p5 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county",null);

        p0.addEdgeTo(p1);

//        ArrayList<Edge> es = new ArrayList<>(p0.getEdges());
//        System.out.println(es.get(0).getJoinability());
//        System.exit(0);
//
        p0.addEdgeTo(p2);
        p0.addEdgeTo(p3);

        p1.addEdgeTo(p4);
        p1.addEdgeTo(p5);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p0);
        hsp.add(p1);
        hsp.add(p2);

        hsp.add(p3);
        hsp.add(p4);
        hsp.add(p5);

        State s = new State(hsp);
        HashMap<MeasureType,Double> weights = new HashMap<>();
        for(int i = 0; i < MeasureType.values().length;i++) {
            weights.put(MeasureType.values()[i],1.0);
        }

        Algorithm a = new Algorithm(new Preference(weights,2,0,2,false, "Arizona"),s);
        System.out.println(a.doJob());
        System.out.println(s);
    }

    @Test
    public void testJSONReading(){
        String path = ""; //place path to file here
        String demVotesMarker = "PRES_DEM08";
        String repVotesMarker = "PRES_REP08";
        try {
            StringBuilder errorPrecincts = new StringBuilder("Error with the following Precinct ids:");
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper  = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData); //gets root of object
            JsonNode featuresNode = rootNode.path("features"); //gets array titled "features"
            Iterator<JsonNode> elements = featuresNode.elements();
            HashMap<Long, Precinct> precincts = new HashMap<Long, Precinct>();
            while(elements.hasNext()){ //change to while
                JsonNode featureZero = elements.next();
                //extract from the geometry.coordinates object
                JsonNode coordinates = featureZero.path("geometry").path("coordinates");
                //GeoJsonObject object = new ObjectMapper().readValue(jsonData, GeoJsonObject.class);
                //object.accept(visitor);
                //JsonNode geometry = featureZero.path("geometry");
                //Polygon polygon = objectMapper.readValue(geometry.toString(), Polygon.class);
                //System.out.println("polygon : " + polygon.getCoordinates());

                System.out.println(coordinates.toString());
                JsonNode propertiesNode = featureZero.path("properties"); //get the properties object from features.
                JsonNode polygonType = featureZero.path("geometry").path("type");
                ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>(); //will hold all the Coordinate objects
                if(polygonType.asText().equals("MultiPolygon")){

                    MultiPolygon m = handleMultiPolygon(coordinates);
                    Geometry g = m.convexHull();
                    coordinateList.addAll(Arrays.asList(g.getCoordinates()));
                    JsonNode precinctId = propertiesNode.path("OID_"); //precinct id
                    //System.out.println("OID_ : " + precinctId);
                    //errorPrecincts.append("\n" + precinctId.toString() + " - Multipolygon.");
                    //continue;
                }
                else{
                    Iterator<JsonNode> coordinatesAtZero = coordinates.elements(); //iterator to get the first element in coord array (only object)
                    if(coordinatesAtZero.hasNext()) {
                        JsonNode coordArray = coordinatesAtZero.next(); //coordinates is an array with one entry that holds arrays
                        Iterator<JsonNode> coordIterator = coordArray.elements();
                        int i = 0;
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
                        System.out.println("Arraylist:\n" + coordinateList);
                    }
                }

                //extract from the properties object
                JsonNode neighbors = propertiesNode.path("neighbors"); //array of neighbors
                System.out.println("neighbors : " + neighbors.toString());
                JsonNode totalpop = propertiesNode.path("Total"); //total population
                System.out.println("Total : " + totalpop.toString());
                JsonNode precinctId = propertiesNode.path("OID_"); //precinct id
                System.out.println("OID_ : " + precinctId);
                JsonNode voteDem = propertiesNode.path(demVotesMarker); //dem votes in 08 election
                System.out.println(demVotesMarker + " : " + voteDem.toString());
                JsonNode voteOther = propertiesNode.path("PRS08_OTH"); //other votes in 08 election
                System.out.println("PRS08_OTH : " + voteOther.toString());
                JsonNode voteRep = propertiesNode.path(repVotesMarker); //rep votes in 08 election
                System.out.println(repVotesMarker + " : " + voteRep.toString());
                JsonNode hispanicPop = propertiesNode.path("Hispanic/Latino"); // hispanic population
                System.out.println("Hispanic/Latino : " + hispanicPop.toString());
                JsonNode asianPop = propertiesNode.path("Asian"); // asian population
                System.out.println("Asian : " + asianPop.toString());
                JsonNode afAmerPop = propertiesNode.path("Black"); // african american population
                System.out.println("Black : " + afAmerPop.toString());
                JsonNode whitePop = propertiesNode.path("White"); // white population
                System.out.println("White : " + whitePop.toString());
                JsonNode nativePop = propertiesNode.path("AI/AN"); //american indians population
                System.out.println("AI/AN : " + nativePop.toString());
                JsonNode county = propertiesNode.path("COUNTY"); //county precinct is in.
                System.out.println("COUNTY : " + county.toString());
                System.out.println();

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
                try {
                    Precinct p = new Precinct(precinctId.asLong(), totalpop.asInt(), new HashSet<Edge>(), d, county.toString(), coordinateList.toArray(new Coordinate[0]));
                    precincts.put(p.getId(), p);
                }
                catch(IllegalArgumentException e){
                    errorPrecincts.append("\n" + precinctId.toString() + " - not a closed linestring.\ncoord: " +coordinateList.toString());

                }


                //System.out.println("Precinct id: " + p.getId());
                //System.out.println("Precinct pop: " + p.getPopulation());
                //System.out.println("Precinct edges: " + p.getEdges());
                //System.out.println("Precinct demographics: " + p.getDemographics());
                //System.out.println("Precinct county: " + p.getCounty());
                //System.out.println("Random vertex: " + p.getPolygon().getCoordinate());
                //TODO
//                Precinct p = new Precinct(precinctId.asLong(), totalpop.asInt(), null, null, county.toString());
//                System.out.println("Precint is:  " + p);
            }
            //now loop once again to create edges
            Iterator<JsonNode> featuresIterator = featuresNode.elements();
            while(featuresIterator.hasNext()){ //change to while
                JsonNode features = featuresIterator.next();
                JsonNode properties = features.path("properties");
                JsonNode neighbors = properties.path("neighbors");
                Long precinctId = properties.path("OID_").asLong();
                Precinct currentPrecinct = precincts.get(precinctId);
                System.out.println(currentPrecinct + " has neighbors " + neighbors.toString());
                Iterator<JsonNode> neighborsIterator = neighbors.elements();
                while(neighborsIterator.hasNext()){
                    Long neighborId = neighborsIterator.next().asLong();
                    Precinct neighborPrecinct = precincts.get(neighborId);
                    Edge e = new Edge(currentPrecinct, neighborPrecinct);
                    boolean noMatch = true;
                    for(Edge edge : currentPrecinct.getEdges()){
                        if(edge.equals(e)){
                            noMatch = false;
                            break;
                        }
                    }
                    if(noMatch){
                        currentPrecinct.getEdges().add(e);
                        System.out.println(currentPrecinct + " added " + e);
                    }
                    noMatch = true;
                    for(Edge edge : neighborPrecinct.getEdges()){
                        if(edge.equals(e)){
                            noMatch = false;
                            break;
                        }
                    }
                    if(noMatch){
                        neighborPrecinct.getEdges().add(e);
                        System.out.println(neighborPrecinct + " added " + e);
                    }
                }
            }



            HashSet<Precinct> precinctSet = new HashSet<Precinct>();
            precinctSet.addAll(precincts.values());
            System.out.println("Following precincts added: ");
            for(Precinct p : precinctSet){
                System.out.println(" " + p + " has " + p.getEdges().size() + " edges.");
            }
            System.out.println(errorPrecincts.toString());
            State s = new State(precinctSet);
            System.out.println("State: " + s);
        }
        catch(IOException e){
            System.out.println("could not read");
            fail();
        }

    }

    public MultiPolygon handleMultiPolygon(JsonNode coordinates){//TODO why is this here
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

    @Test
    public void testFullState() {
        State state = State.getState("New Hampshire");
        Algorithm algorithm = new Algorithm(new PreferenceDAO(1,1,1,1,1,1,1,0,2,2,false,"New Hampshire").makePreference(),state);
        String gps = algorithm.doGraphPartitioning();
        while(!"done".equals(gps)) {
            gps = algorithm.doGraphPartitioning();
        }
        System.out.println(state.getClustersSimple());
    }

    @Test
    public void testEdge() {
        State s = State.getStateOLD("sldkjf");
        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        clusters.sort(Comparator.comparingLong(a -> a.id));
        System.out.println(clusters);
        System.out.println(clusters.get(2).getEdges());
        System.out.println(clusters.get(2).getEdges().size());
    }

}
