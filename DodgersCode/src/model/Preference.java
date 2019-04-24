package model;

public class Preference {
    private double[] weights;
    private int numMaxMinDistricts;

    public Preference(double[] weights, int numMaxMinDistricts) {//TODO this class is maybe unecessary
        this.weights = weights;
        this.numMaxMinDistricts = numMaxMinDistricts;
    }

    public double[] getWeights() {
        return weights;
    }

    public int getNumMaxMinDistricts() {
        return numMaxMinDistricts;
    }
}
