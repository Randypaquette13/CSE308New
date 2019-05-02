package controller;

import model.District;
import model.Precinct;

public class Move {

    private final District from;
    private final District to;
    private final Precinct precinct;

    public Move(District initFrom, District  initTo, Precinct initPrecinct){
        from = initFrom;
        to = initTo;
        precinct = initPrecinct;

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

}
