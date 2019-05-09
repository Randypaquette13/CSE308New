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
        Precinct p1 = new Precinct(10,new HashSet<>(), d1);
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(11,new HashSet<>(), d1);

        Edge e1 = new Edge(p1,p2);
        p1.getEdges().add(e1);
        p2.getEdges().add(e1);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(hsp);

        ArrayList<Cluster> clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

        assertEquals(1, clusters.get(0).getPrecinctSet().size());
        assertEquals(1, clusters.get(1).getPrecinctSet().size());
        Cluster c = s.combinePair(clusters.get(0),clusters.get(1));

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
        Precinct p1 = new Precinct(10,new HashSet<>(), d1);
//        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(11,new HashSet<>(), d1);

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
            voting.put(type, vote);
        }
        Demographics d1 = new Demographics(populations, voting);

        Precinct p1 = new Precinct(10,new HashSet<>(), d1);
        Precinct p2 = new Precinct(10,new HashSet<>(), d1);
        Precinct p3 = new Precinct(10,new HashSet<>(), d1);
        Precinct p4 = new Precinct(10,new HashSet<>(), d1);
        Precinct p5 = new Precinct(10,new HashSet<>(), d1);
        Precinct p6 = new Precinct(10,new HashSet<>(), d1);

        p1.addEdgeTo(p2);
        p1.addEdgeTo(p3);
        p1.addEdgeTo(p4);

        p2.addEdgeTo(p5);
        p2.addEdgeTo(p6);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(hsp);
        ArrayList<Cluster> clusters = new ArrayList<>(s.getClusters());
        clusters.get(0).absorbCluster(new Cluster(p3));
        clusters.get(0).absorbCluster(new Cluster(p4));

        clusters.get(1).absorbCluster(new Cluster(p5));
        clusters.get(1).absorbCluster(new Cluster(p6));
        s.setClusters(clusters);
        s.convertClustersToDistricts();
        assertEquals(2, s.getClusters().size());

        double[] darr = {1.2,1.0,3.2,12,2,1.1};
        Summary summary = new Summary(0.11, darr);
        System.out.println(summary);
    }
}
