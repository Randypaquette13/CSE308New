package model;

import controller.Configuration;
import controller.Move;

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
        final int index = r.nextInt(districtSet.size());

        Iterator<District> iter = districtSet.iterator();
        for(int i = 0; i < index; i++) {
            iter.next();
        }
        final District district = iter.next();

        for(Precinct precinct : district.getPrecinctSet()) {
            for(Edge edge : precinct.getEdges()) {
                System.out.println("move this:" + precinct);
                final Precinct neighbor = (Precinct)(edge.getC1().equals(precinct) ? edge.getC2() : edge.getC1());
                System.out.println("to this:" + neighbor.getDistrict());
                if(edge.getJoinability() > Configuration.ANNEALING_JOINABILITY_THRESHOLD && !neighbor.getDistrict().equals(district) && precinct.getDistrict().continuity(precinct)) {
                    return new Move(district, neighbor.getDistrict(), precinct);
                }
            }
        }
//        findCandidateMove()
        return null;
    }

    /**
     * Get a new candidate pair for graph partitioning. Returns null if no candidate pair is found
     */
    public ClusterPair findCandidateClusterPair() {
        //TODO find candidate cluster pair for graph parittioning
        //TODO: Return null if no candidate pair is found
        return new ClusterPair(null,null);//TODO output
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
}