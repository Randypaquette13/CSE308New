package cse308.server.dao;

import model.MeasureType;
import model.Preference;

import java.util.HashMap;

public class PreferenceDAO {
    private double schwartzbergCompactness;
    private double reackCompactness;
    private double populationEquality;
    private double polsbyCompactness;
    private double convexCompactness;
    private double graphCompactness;
    private double efficiencyGap;
    private int minMajMinDistricts;
    private int maxMajMinDistricts;
    private int numDistricts;
    private boolean graphPartUpdate;
    private String stateName;

    public PreferenceDAO(double schwartzbergCompactness, double reackCompactness, double populationEquality,
                         double polsbyCompactness, double convexCompactness, double graphCompactness,
                         double efficiencyGap, int minMajMinDistricts, int maxMajMinDistricts,
                         int numDistricts, boolean graphPartUpdate, String stateName) {
        this.schwartzbergCompactness = schwartzbergCompactness;
        this.reackCompactness = reackCompactness;
        this.populationEquality = populationEquality;
        this.polsbyCompactness = polsbyCompactness;
        this.convexCompactness = convexCompactness;
        this.graphCompactness = graphCompactness;
        this.efficiencyGap = efficiencyGap;
        this.minMajMinDistricts = minMajMinDistricts;
        this.maxMajMinDistricts = maxMajMinDistricts;
        this.numDistricts = numDistricts;
        this.graphPartUpdate = graphPartUpdate;
        this.stateName = stateName;
    }

    public Preference makePreference(){
        HashMap<MeasureType, Double> hashmap = new HashMap<MeasureType, Double>();
        for(MeasureType m : MeasureType.values()){
            switch (m){
                case SCHWARTZBERG_COMPACTNESS:
                    hashmap.put(m, schwartzbergCompactness/100.0);
                    break;
                case REOCK_COMPACTNESS:
                    hashmap.put(m, reackCompactness/100.0);
                    break;
                case POLSBY_POPPER_COMPACTNESS:
                    hashmap.put(m, polsbyCompactness/100.0);
                    break;
                case CONVEX_HULL_COMPACTNESS:
                    hashmap.put(m, convexCompactness/100.0);
                    break;
                case GRAPH_COMPACTNESS:
                    hashmap.put(m, graphCompactness/100.0);
                    break;
                case EFFICIENCY_GAP:
                    hashmap.put(m, efficiencyGap/100.0);
                    break;
                case POPULATION_EQUALITY:
                    hashmap.put(m, populationEquality/100.0);
                    break;
            }
        }


        return new Preference(hashmap, maxMajMinDistricts, minMajMinDistricts,
                numDistricts, graphPartUpdate, stateName);
    }

    protected PreferenceDAO(){

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