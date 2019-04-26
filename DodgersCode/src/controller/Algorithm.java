package controller;

import model.*;

public class Algorithm {
    Preference pref;
    State s;
    Move candidateMove;

    public Algorithm(Preference pref, State s) {
        this.pref = pref;
        this.s = s;
    }

    public Summary doJob() {//TODO maybe make static

        return null;//TODO
    }

    /**
     * Since the measure is relevant on the district level, the calculation will need
     * to get each measure value for each district
     *
     * @return the output of the objective function
     */
    public double calculateObjectiveFunction() {
        double objFunOutput = 0;
        for(District d : s.getDistrictSet()) {
            for(MeasureType m : MeasureType.values()) {
                objFunOutput += m.calculateMeasure(d) * pref.getWeight(m);
            }
        }
        return objFunOutput;
    }
}
