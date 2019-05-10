package model;

import java.util.HashMap;

public class Preference {
    private final HashMap<MeasureType, Double> weights;
    private final int maxMajMinDistricts;
    private final int minMajMinDistricts;
    private final int numDistricts;

    public Preference(HashMap<MeasureType, Double> weights, int maxMajMinDistricts, int minMajMinDistricts, int numDistricts) {
        this.weights = weights;
        this.maxMajMinDistricts = maxMajMinDistricts;
        this.minMajMinDistricts = minMajMinDistricts;
        this.numDistricts = numDistricts;
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
}
