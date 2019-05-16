package cse308.server.dao;

import model.MeasureType;
import model.Preference;

import java.util.HashMap;

public class BatchPreferencesDAO {
    private double schwartzbergCompactnessMax;
    private double schwartzbergCompactnessMin;
    private double reackCompactnessMax;
    private double reackCompactnessMin;
    private double populationEqualityMax;
    private double populationEqualityMin;
    private double polsbyCompactnessMax;
    private double polsbyCompactnessMin;
    private double convexCompactnessMax;
    private double convexCompactnessMin;
    private double graphCompactnessMax;
    private double graphCompactnessMin;
    private double efficiencyGapMax;
    private double efficiencyGapMin;
    private boolean graphPartUpdate;
    private int maxMajMinDistricts;
    private int minMajMinDistricts;
    private int numDistricts;
    private String stateName;

    public BatchPreferencesDAO(double schwartzbergCompactnessMax, double schwartzbergCompactnessMin,
                               double reackCompactnessMax, double reackCompactnessMin, double populationEqualityMax,
                               double populationEqualityMin, double polsbyCompactnessMax, double polsbyCompactnessMin,
                               double convexCompactnessMax, double convexCompactnessMin, double graphCompactnessMax,
                               double graphCompactnessMin, double efficiencyGapMax, double efficiencyGapMin,
                               boolean graphPartUpdate, int maxMajMinDistricts, int minMajMinDistricts,
                               int numDistricts, String stateName) {
        this.schwartzbergCompactnessMax = schwartzbergCompactnessMax;
        this.schwartzbergCompactnessMin = schwartzbergCompactnessMin;
        this.reackCompactnessMax = reackCompactnessMax;
        this.reackCompactnessMin = reackCompactnessMin;
        this.populationEqualityMax = populationEqualityMax;
        this.populationEqualityMin = populationEqualityMin;
        this.polsbyCompactnessMax = polsbyCompactnessMax;
        this.polsbyCompactnessMin = polsbyCompactnessMin;
        this.convexCompactnessMax = convexCompactnessMax;
        this.convexCompactnessMin = convexCompactnessMin;
        this.graphCompactnessMax = graphCompactnessMax;
        this.graphCompactnessMin = graphCompactnessMin;
        this.efficiencyGapMax = efficiencyGapMax;
        this.efficiencyGapMin = efficiencyGapMin;
        this.graphPartUpdate = graphPartUpdate;
        this.maxMajMinDistricts = maxMajMinDistricts;
        this.minMajMinDistricts = minMajMinDistricts;
        this.numDistricts = numDistricts;
        this.stateName = stateName;
    }

    public Preference makePreferences(){

        HashMap<MeasureType, Double> hashmap = new HashMap<MeasureType, Double>();
        for(MeasureType m : MeasureType.values()){
            switch (m){
                case SCHWARTZBERG_COMPACTNESS:
                    String[] list = new list
                    hashmap.put(m, schwartzbergCompactnessMin schwartzbergCompactnessMax);
                    break;
                case REOCK_COMPACTNESS:
                    hashmap.put(m, reackCompactness);
                    break;
                case POLSBY_POPPER_COMPACTNESS:
                    hashmap.put(m, polsbyCompactness);
                    break;
                case CONVEX_HULL_COMPACTNESS:
                    hashmap.put(m, convexCompactness);
                    break;
                case GRAPH_COMPACTNESS:
                    hashmap.put(m, graphCompactness);
                    break;
                case EFFICIENCY_GAP:
                    hashmap.put(m, efficiencyGap);
                    break;
                case POPULATION_EQUALITY:
                    hashmap.put(m, populationEquality);
                    break;
            }
        }


        return new Preference(hashmap, maxMajMinDistricts, minMajMinDistricts,
                numDistricts, graphPartUpdate, stateName);
    }

    protected PreferencesDAO(){

    }

    public double getSchwartzbergCompactness() {
        return schwartzbergCompactness;
    }

    public void setSchwartzbergCompactness(double schwartzbergCompactness) {
        this.schwartzbergCompactness = schwartzbergCompactness;
    }

    public double getReackCompactness() {
        return reackCompactness;
    }

    public void setReackCompactness(double reackCompactness) {
        this.reackCompactness = reackCompactness;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public double getPolsbyCompactness() {
        return polsbyCompactness;
    }

    public void setPolsbyCompactness(double polsbyCompactness) {
        this.polsbyCompactness = polsbyCompactness;
    }

    public double getConvexCompactness() {
        return convexCompactness;
    }

    public void setConvexCompactness(double convexCompactness) {
        this.convexCompactness = convexCompactness;
    }

    public double getGraphCompactness() {
        return graphCompactness;
    }

    public void setGraphCompactness(double graphCompactness) {
        this.graphCompactness = graphCompactness;
    }

    public double getEfficiencyGap() {
        return efficiencyGap;
    }

    public void setEfficiencyGap(double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }

    public int getMinMajMinDistricts() {
        return minMajMinDistricts;
    }

    public void setMinMajMinDistricts(int minMajMinDistricts) {
        this.minMajMinDistricts = minMajMinDistricts;
    }

    public int getMaxMajMinDistricts() {
        return maxMajMinDistricts;
    }

    public void setMaxMajMinDistricts(int maxMajMinDistricts) {
        this.maxMajMinDistricts = maxMajMinDistricts;
    }

    public int getNumDistricts() {
        return numDistricts;
    }

    public void setNumDistricts(int numDistricts) {
        this.numDistricts = numDistricts;
    }

    public boolean isGraphPartUpdate() {
        return graphPartUpdate;
    }

    public void setGraphPartUpdate(boolean graphPartUpdate) {
        this.graphPartUpdate = graphPartUpdate;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}