import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Algorithm;
import controller.Move;
import cse308.server.dao.JsonDistrictData;
import model.*;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

public class ModelTests {

    @Test
    public void combineClusterPairTest() {
//        System.out.println("COMBINE CLUSTER TEST");

        HashMap<DemographicType, Integer> populations = new HashMap<>();
        HashMap<DemographicType, int[]> voting = new HashMap<>();
        int[] vote = {5,3,2};
        for(DemographicType type : DemographicType.values()) {
            populations.put(type, 10);
            voting.put(type, vote);
        }
        Demographics d1 = new Demographics(populations, voting);

        int id = 0;
//        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(id++,10,new HashSet<>(), d1,"my county");
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(id++,11,new HashSet<>(), d1,"my county");

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
        HashMap<DemographicType, Integer> populations = new HashMap<>();
        HashMap<DemographicType, int[]> voting = new HashMap<>();
        int[] vote = {5,3,2};
        for(DemographicType type : DemographicType.values()) {
            populations.put(type, 10);
            voting.put(type, vote);
        }
        Demographics d1 = new Demographics(populations, voting);

        int id = 0;
//        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(id++,10,new HashSet<>(), d1,"my county");
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(id++,11,new HashSet<>(), d1,"my county");

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
        s.reset();
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
        HashMap<DemographicType, Integer> populations = new HashMap<>();
        HashMap<DemographicType, int[]> voting = new HashMap<>();
        int[] vote = {5,3,2};
        for(DemographicType type : DemographicType.values()) {
            populations.put(type, 10);
            voting.put(type, vote.clone());
        }
        Demographics d1 = new Demographics(populations, voting);
        int id = 0;
        Precinct p0 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p2 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p3 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p4 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p5 = new Precinct(id++,10,new HashSet<>(), Demographics.getDemographicTest(),"my county");

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
//        int id = 0;
//        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
//        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
//        Precinct p2 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
//        Precinct p3 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
//        Precinct p4 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
//        Precinct p5 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
//
//        p0.addEdgeTo(p1);
//
//        p0.addEdgeTo(p2);
//        p0.addEdgeTo(p3);
////        System.out.println(p0 + " edges: " + p0.getEdges().size());
//
//        p1.addEdgeTo(p4);
//        p1.addEdgeTo(p5);
//
//        HashSet<Precinct> hsp = new HashSet<>();
//        hsp.add(p0);
//        hsp.add(p1);
//        hsp.add(p2);
//
//        hsp.add(p3);
//        hsp.add(p4);
//        hsp.add(p5);

        State s = State.getState("sdljf");
        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        clusters.sort(Comparator.comparingLong(a -> a.id));
        Set<Cluster> newClusters = new HashSet<>();
        newClusters.add(s.combinePair(clusters.get(0),clusters.get(2)));
        newClusters.add(s.combinePair(clusters.get(0),clusters.get(3)));

        newClusters.add(s.combinePair(clusters.get(1),clusters.get(4)));
        newClusters.add(s.combinePair(clusters.get(1),clusters.get(5)));

        s.setClusters(newClusters);
        s.convertClustersToDistricts();

//        double[] darr = new double[MeasureType.values().length];
//        Arrays.fill(darr, 1.0);
        HashMap<MeasureType,Double> weights = new HashMap<>();
        for(int i = 0; i < MeasureType.values().length;i++) {
            weights.put(MeasureType.values()[i],1.0);
        }

        Algorithm a = new Algorithm(new Preference(weights,2,0,2,true, "Arizona"),s);
        int i = 0;
        Summary sum = a.doSimulatedAnnealing();
//        while (sum.getMove() != null) {
//            i++;
//            sum = a.doSimulatedAnnealing();
//        } System.out.println(++i);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(sum.toString());
        System.out.println(s.getClusters());
    }

    @Test
    public void majorityMinorityTest(){
        int id = 0;
        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
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
        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p2 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p3 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p4 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p5 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");

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
        Precinct p0 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p2 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p3 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p4 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p5 = new Precinct(id++,60,new HashSet<>(), Demographics.getDemographicTest(),"my county");

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
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper  = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData); //gets root of object
            JsonNode featuresNode = rootNode.path("features"); //gets array titled "features"
            Iterator<JsonNode> elements = featuresNode.elements();
            if(elements.hasNext()){
                JsonNode featureZero = elements.next();
                //extract from the geometry.coordinates object
                JsonNode coordinates = featureZero.path("geometry").path("coordinates");
                Iterator<JsonNode> coordinatesAtZero = coordinates.elements(); //iterator to get the first element in coord array (only object)
                if(coordinatesAtZero.hasNext()){
                    JsonNode coordArray = coordinatesAtZero.next(); //coordinates is an array with one entry that holds arrays
                    Iterator<JsonNode> coordIterator = coordArray.elements();
                    int i = 0;
                    while(coordIterator.hasNext()){
                        JsonNode coordinate = coordIterator.next(); //get the next coordinate array.
                        System.out.println("Coordinate at " + i++ + " is " + coordinate.toString());
                        Iterator<JsonNode> coordValue = coordinate.elements(); //to get the two values from the coordinate
                        double coord1 = coordValue.next().asDouble();
                        double coord2 = coordValue.next().asDouble();
                        Coordinate coord = new Coordinate(coord1, coord2); //read the two values into a Coordinate obj
                        System.out.println("Coord is " + coord.toString());
                    }
                }




                //System.out.println("coordinates : " + coordinates.toString());
                //extract from the properties object
                JsonNode propertiesNode = featureZero.path("properties"); //get the properties object from features.
                JsonNode neighbors = propertiesNode.path("neighbors"); //array of neighbors
                System.out.println("neighbors : " + neighbors.toString());
                JsonNode totalpop = propertiesNode.path("Total"); //total population
                System.out.println("Total : " + totalpop.toString());
                JsonNode precinctId = propertiesNode.path("OID_"); //precinct id
                System.out.println("OID_ : " + precinctId);
                JsonNode voteDem = propertiesNode.path("PRS08_DEM"); //dem votes in 08 election
                System.out.println("PRS08_DEM : " + voteDem.toString());
                JsonNode voteOther = propertiesNode.path("PRS08_OTH"); //other votes in 08 election
                System.out.println("PRS08_DEM : " + voteOther.toString());
                JsonNode voteRep = propertiesNode.path("PRS08_OTH"); //rep votes in 08 election
                System.out.println("PRS08_DEM : " + voteRep.toString());
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

                Precinct p = new Precinct(precinctId.asLong(), totalpop.asInt(), null, null, county.toString());
                System.out.println("Precint is:  " + p);
            }
        }
        catch(IOException e){
            System.out.println("could not read");
            fail();
        }

    }
}
