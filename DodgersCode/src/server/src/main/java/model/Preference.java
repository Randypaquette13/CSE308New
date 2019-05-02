package model;

public class Preference {
    private final double[] weights;
    private final int numMaxMinDistricts;
    private final int numDistricts;

    public Preference(double[] weights, int numMaxMinDistricts, int numDistricts) {
        this.weights = weights;
        this.numMaxMinDistricts = numMaxMinDistricts;
        this.numDistricts = numDistricts;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getWeight(MeasureType m) {//TODO
        return -1;
    }

    public int getNumMaxMinDistricts() {
        return numMaxMinDistricts;
    }

    public double getNumDistricts() {
        return numDistricts;
    }
}
