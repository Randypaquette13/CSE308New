package model;

public class Summary {

    State state;
    double objectiveFunctionScore; //The total function score
    double[] measureScores; //individual scores
    //TODO other summary info?

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
