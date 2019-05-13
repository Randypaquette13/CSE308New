package cse308.server.dao;

import model.MeasureType;
import model.Preference;

import java.util.HashMap;

public class PreferencesDAO {
    private double compactness;
    private double contiguity;
    private double populationEquality;
    private double partisanFairness;
    private double racial;
    private int minMajMinDistricts;
    private int maxMajMinDistricts;
    private int numDistricts;
    private boolean graphPartUpdate;
    private String stateName;

    public PreferencesDAO(double compactness, double contiguity, double populationEquality,
                          double partisanFairness, double racial, int minMajMinDistricts,
                          int maxMajMinDistricts, int numDistricts, boolean graphPartUpdate,
                          String stateName) {
        this.compactness = compactness;
        this.contiguity = contiguity;
        this.populationEquality = populationEquality;
        this.partisanFairness = partisanFairness;
        this.racial = racial;
        this.minMajMinDistricts = minMajMinDistricts;
        this.maxMajMinDistricts = maxMajMinDistricts;
        this.numDistricts = numDistricts;
        this.graphPartUpdate = graphPartUpdate;
        this.stateName = stateName;
    }

    public Preference makePreferences(){

        HashMap<MeasureType, Double> hashmap = new HashMap<MeasureType, Double>();
        for(MeasureType m : MeasureType.values()){
            switch (m){
                case SCHWARTZBERG_COMPACTNESS:

                    break;
                case REOCK_COMPACTNESS:

                    break;
                case POLSBY_POPPER_COMPACTNESS:

                    break;
                case CONVEX_HULL_COMPACTNESS:

                    break;
                case GRAPH_COMPACTNESS:

                    break;
                case EFFICIENCY_GAP:

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
    public double getCompactness() {
        return compactness;
    }

    public void setCompactness(double compactness) {
        this.compactness = compactness;
    }

    public double getContiguity() {
        return contiguity;
    }

    public void setContiguity(double contiguity) {
        this.contiguity = contiguity;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public double getPartisanFairness() {
        return partisanFairness;
    }

    public void setPartisanFairness(double partisanFairness) {
        this.partisanFairness = partisanFairness;
    }

    public double getRacial() {
        return racial;
    }

    public void setRacial(double racial) {
        this.racial = racial;
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
}