package controller;

import model.*;

public class Algorithm {
    Preference pref;
    State state;

    private double lastObjFunVal = 0;

    public Algorithm(Preference pref, State state) {
        this.pref = pref;
        this.state = state;
    }

    public Summary doJob() {//TODO maybe make static
        //you must reset the state so we dont have to make database calls
        state.reset(pref);
        doGraphPartitioning();//TODO this is a placeHolder

        int annealingSteps = 0;
        Move candidateMove;
        //anneal until the objective function output is acceptable or the max steps is reached
        while(calculateObjectiveFunction() < Configuration.OBJECTIVE_FUNCTION_GOAL && annealingSteps < Configuration.MAX_ANNEALING_STEPS) {
            candidateMove = state.findCandidateMove();

            if(candidateMove != null) {
                state.doMove(candidateMove);
                double currObjFunVal = calculateObjectiveFunction();

                if((currObjFunVal - lastObjFunVal) > Configuration.OBJECTIVE_FUNCTION_MIN_CHANGE) {
                    lastObjFunVal = currObjFunVal;
                } else {
                    state.undoMove();
                }
            }
            annealingSteps++;
        }

        return new Summary(state,lastObjFunVal,null);//TODO this is a placeholder
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
                objFunOutput += m.calculateMeasure(d) * pref.getWeight(m);
            }
        }
        return objFunOutput;
    }

    /**
     * this is a placeholder
     * TODO
     */
    private void doGraphPartitioning() {
//        pref.getNumDistricts()
    }
}
