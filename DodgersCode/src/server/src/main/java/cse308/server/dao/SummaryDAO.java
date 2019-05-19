package cse308.server.dao;

public class SummaryDAO {
    private long toID;
    private long precinctID;
    private double objectiveFunctionScore; //The total function score
    private double[] measureScores; //individual scores
    private int MajMinDistricts;

    public SummaryDAO(long toID, long precinctID, double objectiveFunctionScore, double[] measureScores, int MajMinDistricts) {
        this.toID = toID;
        this.precinctID = precinctID;
        this.objectiveFunctionScore = objectiveFunctionScore;
        this.measureScores = measureScores;
        this.MajMinDistricts = MajMinDistricts;
    }

    public long getToID() {
        return toID;
    }

    public void setToID(long toID) {
        this.toID = toID;
    }

    public long getPrecinctID() {
        return precinctID;
    }

    public void setPrecinctID(long precinctID) {
        this.precinctID = precinctID;
    }

    public double getObjectiveFunctionScore() {
        return objectiveFunctionScore;
    }

    public void setObjectiveFunctionScore(double objectiveFunctionScore) {
        this.objectiveFunctionScore = objectiveFunctionScore;
    }

    public double[] getMeasureScores() {
        return measureScores;
    }

    public void setMeasureScores(double[] measureScores) {
        this.measureScores = measureScores;
    }

    public int getMajMinDistricts() {
        return MajMinDistricts;
    }

    public void setMajMinDistricts(int MajMinDistricts) {
        this.MajMinDistricts = MajMinDistricts;
    }

}
