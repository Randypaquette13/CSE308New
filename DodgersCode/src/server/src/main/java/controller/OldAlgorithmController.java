package controller;

import model.Preference;
import model.State;
import model.Summary;

import java.util.Collection;
import java.util.LinkedList;

@Deprecated
public class OldAlgorithmController {

    Collection<Preference> prefs;
    State s;

    public OldAlgorithmController(Collection<Preference> prefs, String state) {//TODO do we even need this? maybe make all static
        this.prefs = prefs;
        this.s = null;//EntityManager.loadState(state);//TODO EntityManager needs to be made
    }

    public Collection<Summary> startJobs() {
        Collection<Summary> summaryBatch = new LinkedList<>();

        for(Preference p : prefs) {
            summaryBatch.add(new Algorithm(p,s).doJob());//TODO is this all we need algorithm for?
        }
        return summaryBatch;
    }
}