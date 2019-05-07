import model.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ModelTests {

    @Test
    public void combineClusterPairTest() {
        System.out.println("COMBINE CLUSTER TEST");
        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(10,new HashSet<>(), d1);
        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(11,new HashSet<>(), d2);

        Edge e1 = new Edge(p1,p2, 0);
        p1.getEdges().add(e1);
        p2.getEdges().add(e1);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(new HashSet<>(), hsp, 0);

        ArrayList<Cluster> clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

        assertEquals(1, clusters.get(0).getPrecinctSet().size());
        assertEquals(1, clusters.get(1).getPrecinctSet().size());
        s.combinePair(clusters.get(0),clusters.get(1));

        assertEquals( 1, s.getClusters().size());
        Cluster c = s.getClusters().iterator().next();
        double[] demo = {2,4,4,5,4};
        for(int i = 0; i < demo.length; i++) {
            assertEquals(demo[i], c.getDemographicValues()[i], 0.001);
        }
        assertEquals(0, c.getEdges().size());
        assertEquals(21, c.getPopulation());
        assertEquals(2, c.getPrecinctSet().size());

        s.reset(new Preference(new double[0],10,10));

    }

    @Test
    public void testReset() {
        System.out.println("TEST RESET");
        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(10,new HashSet<>(), d1);
        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(11,new HashSet<>(), d2);

        Edge e1 = new Edge(p1,p2, 0);
        p1.getEdges().add(e1);
        p2.getEdges().add(e1);

        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(new HashSet<>(), hsp, 0);



        ArrayList<Cluster> clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

        System.out.println(s);
        s.combinePair(clusters.get(0),clusters.get(1));
        System.out.println(s);
        s.reset(new Preference(null, 0,0));
        System.out.println(s);

        clusters = new ArrayList<>();
        clusters.addAll(s.getClusters());

        System.out.println(clusters.get(0));
        System.out.println(clusters.get(1));

        assertEquals(1, clusters.get(0).getPrecinctSet().size());
        assertEquals(1, clusters.get(1).getPrecinctSet().size());

    }
}
