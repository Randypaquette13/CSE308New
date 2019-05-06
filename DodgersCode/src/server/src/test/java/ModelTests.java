import model.Cluster;
import model.Precinct;
import model.Preference;
import model.State;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ModelTests {

    @Test
    public void combineClusterPairTest() {
        double[] d1 = {0,2,2,2,2};
        Precinct p1 = new Precinct(0,10,new HashSet<>(), d1);
        double[] d2 = {2,2,2,3,2};
        Precinct p2 = new Precinct(1,11,new HashSet<>(), d2);
        HashSet<Precinct> hsp = new HashSet<>();
        hsp.add(p1);
        hsp.add(p2);

        State s = new State(new HashSet<>(), hsp, 0);

        Iterator<Cluster> iter = s.getClusters().iterator();

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
        assertEquals(0, c.getEdgeSet().size());
        assertEquals(21, c.getPopulation());
        assertEquals(2, c.getPrecinctSet().size());



    }
}
