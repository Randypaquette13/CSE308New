package model;

public class Preference {
    private double[] weights;//TODO use a datatyppe that maps weights to measure skrrrrr
    private int numMaxMinDistricts;
    private int numDistricts;

    public Preference(double[] weights, int numMaxMinDistricts, int numDistricts) {//TODO this class is maybe unecessary
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
