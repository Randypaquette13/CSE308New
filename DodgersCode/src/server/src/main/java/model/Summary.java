package model;

public class Summary {

    private final State state;
    private final double objectiveFunctionScore; //The total function score
    private final double[] measureScores; //individual scores
    private static long numSummaries = 0;
    private final long id;

    public Summary(State state, double objectiveFunctionScore, double[] measureScores) {
        this.state = state;
        this.objectiveFunctionScore = objectiveFunctionScore;
        this.measureScores = measureScores;
        id = numSummaries++;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(state.toString() + " tScore: " + objectiveFunctionScore + " mScores: ");
        for(double mScore : measureScores) {
            sb.append(mScore);
            sb.append(",");
        }

        return sb.toString();
    }
}
