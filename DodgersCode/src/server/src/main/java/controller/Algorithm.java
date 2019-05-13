package controller;

import model.*;

import java.util.*;

public class Algorithm {
    private Preference pref;
    private State state;
    private int annealingSteps = 0;
    private double lastObjFunVal = 0;

    public Algorithm(Preference pref, State state) {
        this.pref = pref;
        this.state = state;
    }

    /**
     * This is just an example of how to run the algorithm
     */
    public Summary doJob() {
        state.reset();
        String gps = doGraphPartitioning();
        while(!"done".equals(gps)) {
            gps = doGraphPartitioning();
        }

        Summary s = doSimulatedAnnealing();
        while (s.getMove() != null) {
            s = doSimulatedAnnealing();
        }
        return s;
    }

    /**
     * Since the measure is relevant on the district level, the calculation will need
     * to get each measure value for each district
     *
     * @return the output of the objective function
     */
    private double calculateObjectiveFunction() {
        double objFunOutput = 0;
        for(District d : state.getDistrictSet()) {
            for(MeasureType m : MeasureType.values()) {
                objFunOutput += ((m.calculateMeasure(d) * pref.getWeight(m)) / (state.getDistrictSet().size() * MeasureType.values().length));
            }
        }
        return objFunOutput;
    }

    private double[] calculateTotalMeasuresScores() {
        double[] measureScores = new double[MeasureType.values().length];

        for(District d : state.getDistrictSet()) {
            for(int ii = 0; ii < MeasureType.values().length; ii++) {
                measureScores[ii] = MeasureType.values()[ii].calculateMeasure(d);
            }
        }
        for(int ii = 0; ii < measureScores.length; ii++) {
            measureScores[ii] = measureScores[ii]/(double)state.getDistrictSet().size();
        }
        return measureScores;
    }

    public String doGraphPartitioning() {
        String s;
        //you must reset the state so we dont have to make extra database calls
        System.out.println("clusters size: " + state.getClusters().size());
        System.out.println("target num dist: " + pref.getNumDistricts());
        if(state.getClusters().size() != pref.getNumDistricts()) {
            int targetNumClusters = (int)Math.ceil(state.getClusters().size() / 2);
            int maxTargetPop = (int)Math.ceil(state.getPopulation() / targetNumClusters);
            int minTargetPop = 0;   //TODO: load percentage to ignore from config file

            ((List<Cluster>) state.getClusters()).sort(Comparator.comparingInt(Cluster::getPopulation));

            Collection<Cluster> mergedClusters = new LinkedList<>();
            while(!state.getClusters().isEmpty()) {
                final ClusterPair clusterPair = state.findCandidateClusterPair();
                if(clusterPair == null){
                    break;
                }
                System.out.println("found cluster pair: " + clusterPair);
                Cluster c = state.combinePair(clusterPair.getC1(), clusterPair.getC2());
                mergedClusters.add(c);
            }
            (state.getClusters()).addAll(mergedClusters);
            s = state.getClusters().toString();
        } else {
            s = "done";
        }
        return s;
    }

    public Summary doSimulatedAnnealing() {
        System.out.println("\n\tSTARTED SIM ANNEALING STEP");
        if(state.getDistrictSet().size() == 0) {
            state.convertClustersToDistricts();
        }

        Move candidateMove = null;
        //anneal until the objective function output is acceptable or the max steps is reached
        if(calculateObjectiveFunction() < Configuration.OBJECTIVE_FUNCTION_GOAL && annealingSteps < Configuration.MAX_ANNEALING_STEPS) {
            candidateMove = state.findCandidateMove();
            if(candidateMove != null) {
//                System.out.println(candidateMove);

                final int oldMajMinDistNum = state.numMaxMinDists();
                state.doMove(candidateMove);
                final int newMajMinDistNum = state.numMaxMinDists();

                if(newMajMinDistNum < pref.getMinMajMinDistricts() && oldMajMinDistNum > newMajMinDistNum) {//if it is less than min and it went down
                    System.out.println("undoing move");
                    state.undoMove();
                } else if(newMajMinDistNum > pref.getMaxMajMinDistricts() && oldMajMinDistNum < newMajMinDistNum) {//if it is greater than max and it went up
                    System.out.println("undoing move");
                    state.undoMove();
                } else {
                    final double currObjFunVal = calculateObjectiveFunction();
                    if((currObjFunVal - lastObjFunVal) > Configuration.OBJECTIVE_FUNCTION_MIN_CHANGE) {
                        lastObjFunVal = currObjFunVal;
                    } else {
                        System.out.println("undoing move");
                        state.undoMove();
                    }
                }
            } else {
                System.out.println("NO CANDIDATE MOVE FOUND");
            }
            annealingSteps++;
        } else {
            System.out.println("SIM END CONDITION MET");
        }
        System.out.println("\tENDED SIM ANNEALING STEP");
        return new Summary(lastObjFunVal,calculateTotalMeasuresScores(), candidateMove);
    }
}
