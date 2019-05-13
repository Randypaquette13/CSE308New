import controller.Algorithm;
import controller.Move;
import model.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

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

//        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(10,new HashSet<>(), d1,"my county");
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(11,new HashSet<>(), d1,"my county");

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

//        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(10,new HashSet<>(), d1,"my county");
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(11,new HashSet<>(), d1,"my county");

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

        Precinct p0 = new Precinct(10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p2 = new Precinct(10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p3 = new Precinct(10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p4 = new Precinct(10,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p5 = new Precinct(10,new HashSet<>(), Demographics.getDemographicTest(),"my county");

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
        Precinct p0 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p2 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p3 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p4 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p5 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");

        p0.addEdgeTo(p1);

        p0.addEdgeTo(p2);
        p0.addEdgeTo(p3);
//        System.out.println(p0 + " edges: " + p0.getEdges().size());

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
        System.out.println(a.doSimulatedAnnealing());
//        System.out.println(a.doSimulatedAnnealing());
//        System.out.println(a.doSimulatedAnnealing());
//        System.out.println(a.doSimulatedAnnealing());
//        System.out.println(a.doSimulatedAnnealing());
//        System.out.println(a.doSimulatedAnnealing());
//        System.out.println(a.doSimulatedAnnealing());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(s.getClusters());
    }

    @Test
    public void majorityMinorityTest(){
        Precinct p0 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
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
        Precinct p0 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p1 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p2 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p3 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p4 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");
        Precinct p5 = new Precinct(60,new HashSet<>(), Demographics.getDemographicTest(),"my county");

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
//        System.out.println(s.toString());
//        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
//        clusters.sort(Comparator.comparingLong(a -> a.id));
//        System.out.println(clusters);

        HashMap<MeasureType,Double> weights = new HashMap<>();
        for(int i = 0; i < MeasureType.values().length;i++) {
            weights.put(MeasureType.values()[i],1.0);
        }

        Algorithm a = new Algorithm(new Preference(weights,2,0,2,false, "Arizona"),s);
        System.out.println(a.doGraphPartitioning());
        System.out.println(s);
        System.out.println(a.doGraphPartitioning());
        System.out.println(s);
        System.out.println(a.doGraphPartitioning());
        System.out.println(s);

//        System.out.println(a.doGraphPartitioning());
//        String gps = "";
//        while(!"done".equals(gps)) {
//            gps = a.doGraphPartitioning();
//            System.out.println(gps);
//        }


    }

}
