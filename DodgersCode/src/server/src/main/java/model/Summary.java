package model;

import controller.Move;

import java.io.Serializable;

public class Summary implements Serializable {

    private final double objectiveFunctionScore; //The total function score
    private final double[] measureScores; //individual scores
    private final Move move;
    private static long numSummaries = 0;
    private final long id;

    public Summary(double objectiveFunctionScore, double[] measureScores, Move move) {
        this.objectiveFunctionScore = objectiveFunctionScore;
        this.measureScores = measureScores;
        this.move = move;
        id = numSummaries++;
    }

    public double getObjectiveFunctionScore() {
        return objectiveFunctionScore;
    }

    public double[] getMeasureScores() {
        return measureScores;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(move != null) {
            sb.append("id: " + id + " move: " + move);
        } else {
            sb.append("id: " + id + " tScore: " + objectiveFunctionScore + " mScores: ");
            for(int i = 0; i < measureScores.length; i++) {
                sb.append(MeasureType.values()[i]);
                sb.append(":");
                sb.append(measureScores[i]);
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
