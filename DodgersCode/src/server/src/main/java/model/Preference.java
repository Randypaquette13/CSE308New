package model;

import java.util.HashMap;

public class Preference {
    private final HashMap<MeasureType, Double> weights;
    private final int maxMajMinDistricts;
    private final int minMajMinDistricts;
    private final int numDistricts;
    private final boolean graphPartUpdate;
    private final String stateName;

    public Preference(HashMap<MeasureType, Double> weights, int maxMajMinDistricts, int minMajMinDistricts, int numDistricts, boolean graphPartUpdate, String stateName) {
        this.weights = weights;
        this.maxMajMinDistricts = maxMajMinDistricts;
        this.minMajMinDistricts = minMajMinDistricts;
        this.numDistricts = numDistricts;
        this.graphPartUpdate = graphPartUpdate;
        this.stateName = stateName;
    }

    public HashMap<MeasureType, Double> getWeights() {
        return weights;
    }

    public double getWeight(MeasureType m) {
        return weights.get(m);
    }

    public int getMaxMajMinDistricts() {
        return maxMajMinDistricts;
    }

    public int getMinMajMinDistricts() {
        return minMajMinDistricts;
    }

    public double getNumDistricts() {
        return numDistricts;
    }

    public boolean isGraphPartUpdate() {
        return graphPartUpdate;
    }
}
