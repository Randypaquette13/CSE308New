package controller;

import model.District;
import model.Precinct;

public class Move {

    private final District from;
    private final District to;
    private final Precinct precinct;

    public Move(District from, District  to, Precinct precinct){
        this.from = from;
        this.to = to;
        this.precinct = precinct;

    }

    public District getFrom(){
        return from;
    }

    public District getTo(){
        return to;
    }

    public Precinct getPrecinct(){
        return precinct;
    }

    @Override
    public String toString() {
        return from + " " + to + " P:" + precinct;
    }
}
