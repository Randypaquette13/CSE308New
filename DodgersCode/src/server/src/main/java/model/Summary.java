package model;

public class Summary {

    private final State state;
    private final double objectiveFunctionScore; //The total function score
    private final double[] measureScores; //individual scores

    public Summary(State state, double objectiveFunctionScore, double[] measureScores) {
        this.state = state;
        this.objectiveFunctionScore = objectiveFunctionScore;
        this.measureScores = measureScores;
    }

    public State getState() {
        return state;
    }

    public double getObjectiveFunctionScore() {
        return objectiveFunctionScore;
    }

    public double[] getMeasureScores() {
        return measureScores;
    }
}
