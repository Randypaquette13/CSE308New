package cse308.server.dao;

import model.MeasureType;
import model.Preference;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BatchedPreferencesDAO {
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
    private int numRuns;
    private int numDistricts;
    private String stateName;

    public BatchedPreferencesDAO(double schwartzbergCompactnessMax, double schwartzbergCompactnessMin,
                               double reackCompactnessMax, double reackCompactnessMin, double populationEqualityMax,
                               double populationEqualityMin, double polsbyCompactnessMax, double polsbyCompactnessMin,
                               double convexCompactnessMax, double convexCompactnessMin, double graphCompactnessMax,
                               double graphCompactnessMin, double efficiencyGapMax, double efficiencyGapMin,
                               boolean graphPartUpdate, int maxMajMinDistricts, int minMajMinDistricts, int numRuns,
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
        this.numRuns = numRuns;
        this.numDistricts = numDistricts;
        this.stateName = stateName;
    }

    public List<Preference> makePreferences(){
        LinkedList<Preference> preferences = new LinkedList<>();

        for(int i=0; i < numRuns; i++ ) {
            HashMap<MeasureType, Double> hashmap = new HashMap<MeasureType, Double>();
            for(MeasureType m : MeasureType.values()){
                switch (m){
                    case SCHWARTZBERG_COMPACTNESS:
                        hashmap.put(m, ((Math.random() * (schwartzbergCompactnessMax-schwartzbergCompactnessMin)) + schwartzbergCompactnessMin)/100.0);
                        break;
                    case REOCK_COMPACTNESS:
                        hashmap.put(m, ((Math.random() * (reackCompactnessMax-reackCompactnessMin)) + reackCompactnessMin)/100.0);
                        break;
                    case POLSBY_POPPER_COMPACTNESS:
                        hashmap.put(m, ((Math.random() * (polsbyCompactnessMax-polsbyCompactnessMin)) + polsbyCompactnessMin)/100.0);
                        break;
                    case CONVEX_HULL_COMPACTNESS:
                        hashmap.put(m, ((Math.random() * (convexCompactnessMax-convexCompactnessMin)) + convexCompactnessMin)/100.0);
                        break;
                    case GRAPH_COMPACTNESS:
                        hashmap.put(m, ((Math.random() * (graphCompactnessMax-graphCompactnessMin)) + graphCompactnessMin)/100.0);
                        break;
                    case EFFICIENCY_GAP:
                        hashmap.put(m, ((Math.random() * (efficiencyGapMax-efficiencyGapMin)) + efficiencyGapMin)/100.0);
                        break;
                    case POPULATION_EQUALITY:
                        hashmap.put(m, ((Math.random() * (populationEqualityMax-populationEqualityMin)) + populationEqualityMin)/100.0);
                        break;
                }
            }
            preferences.add(new Preference(hashmap,maxMajMinDistricts,minMajMinDistricts,numDistricts,false, stateName));
        }
        return preferences;
    }

    protected BatchedPreferencesDAO(){

    }

    public double getSchwartzbergCompactnessMax() {
        return schwartzbergCompactnessMax;
    }

    public void setSchwartzbergCompactnessMax(double schwartzbergCompactnessMax) {
        this.schwartzbergCompactnessMax = schwartzbergCompactnessMax;
    }

    public double getSchwartzbergCompactnessMin() {
        return schwartzbergCompactnessMin;
    }

    public void setSchwartzbergCompactnessMin(double schwartzbergCompactnessMin) {
        this.schwartzbergCompactnessMin = schwartzbergCompactnessMin;
    }

    public double getReackCompactnessMax() {
        return reackCompactnessMax;
    }

    public void setReackCompactnessMax(double reackCompactnessMax) {
        this.reackCompactnessMax = reackCompactnessMax;
    }

    public double getReackCompactnessMin() {
        return reackCompactnessMin;
    }

    public void setReackCompactnessMin(double reackCompactnessMin) {
        this.reackCompactnessMin = reackCompactnessMin;
    }

    public double getPopulationEqualityMax() {
        return populationEqualityMax;
    }

    public void setPopulationEqualityMax(double populationEqualityMax) {
        this.populationEqualityMax = populationEqualityMax;
    }

    public double getPopulationEqualityMin() {
        return populationEqualityMin;
    }

    public void setPopulationEqualityMin(double populationEqualityMin) {
        this.populationEqualityMin = populationEqualityMin;
    }

    public double getPolsbyCompactnessMax() {
        return polsbyCompactnessMax;
    }

    public void setPolsbyCompactnessMax(double polsbyCompactnessMax) {
        this.polsbyCompactnessMax = polsbyCompactnessMax;
    }

    public double getPolsbyCompactnessMin() {
        return polsbyCompactnessMin;
    }

    public void setPolsbyCompactnessMin(double polsbyCompactnessMin) {
        this.polsbyCompactnessMin = polsbyCompactnessMin;
    }

    public double getConvexCompactnessMax() {
        return convexCompactnessMax;
    }

    public void setConvexCompactnessMax(double convexCompactnessMax) {
        this.convexCompactnessMax = convexCompactnessMax;
    }

    public double getConvexCompactnessMin() {
        return convexCompactnessMin;
    }

    public void setConvexCompactnessMin(double convexCompactnessMin) {
        this.convexCompactnessMin = convexCompactnessMin;
    }

    public double getGraphCompactnessMax() {
        return graphCompactnessMax;
    }

    public void setGraphCompactnessMax(double graphCompactnessMax) {
        this.graphCompactnessMax = graphCompactnessMax;
    }

    public double getGraphCompactnessMin() {
        return graphCompactnessMin;
    }

    public void setGraphCompactnessMin(double graphCompactnessMin) {
        this.graphCompactnessMin = graphCompactnessMin;
    }

    public double getEfficiencyGapMax() {
        return efficiencyGapMax;
    }

    public void setEfficiencyGapMax(double efficiencyGapMax) {
        this.efficiencyGapMax = efficiencyGapMax;
    }

    public double getEfficiencyGapMin() {
        return efficiencyGapMin;
    }

    public void setEfficiencyGapMin(double efficiencyGapMin) {
        this.efficiencyGapMin = efficiencyGapMin;
    }

    public boolean isGraphPartUpdate() {
        return graphPartUpdate;
    }

    public void setGraphPartUpdate(boolean graphPartUpdate) {
        this.graphPartUpdate = graphPartUpdate;
    }

    public int getMaxMajMinDistricts() {
        return maxMajMinDistricts;
    }

    public void setMaxMajMinDistricts(int maxMajMinDistricts) {
        this.maxMajMinDistricts = maxMajMinDistricts;
    }

    public int getMinMajMinDistricts() {
        return minMajMinDistricts;
    }

    public void setMinMajMinDistricts(int minMajMinDistricts) {
        this.minMajMinDistricts = minMajMinDistricts;
    }

    public int getNumRuns() {
        return numRuns;
    }

    public void setNumRuns(int numRuns) {
        this.numRuns = numRuns;
    }

    public int getNumDistricts() {
        return numDistricts;
    }

    public void setNumDistricts(int numDistricts) {
        this.numDistricts = numDistricts;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}