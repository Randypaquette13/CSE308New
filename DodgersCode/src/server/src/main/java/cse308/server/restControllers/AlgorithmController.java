package cse308.server.restControllers;

import controller.Algorithm;
import cse308.server.dao.*;
import model.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * This class specifies the endpoints and behavior used to run the algorithm.
 */
@RestController
public class AlgorithmController {

    private State state= null;
    private Algorithm algorithm;
    private String currentState = null;

    /**
     * This method handles running the graph partitioning portion of the algorithm.
     * @return  unknown
     */
    @RequestMapping("/runGraphPartitioning")
    public Collection<long[]> doGraphPartitioning(@RequestBody PreferenceDAO preference) {
        //TODO: Load state object from DB and set it to the private state obj
        Preference p = preference.makePreference();
        if(state == null) {
            state = State.getState(p.getStateName());
            currentState = preference.getStateName();
            if(state == null) {
                System.out.println("MEGA FUCKED");
            }
        } else if ((state.isGPDone) && currentState.equals(preference.getStateName())){
//            state.reset();
        }
//        else if (state.isGPDone){
//            state = State.getState(p.getStateName());
//            currentState = preference.getStateName();
//        }

        algorithm = new Algorithm(p, state);
        if (preference.isGraphPartUpdate()) {
            System.out.println("TRYING GP WITH UPDATES");
            if(state.isGPDone) {
                return null;
            }
            if("done".equals(algorithm.doGraphPartitioning())){
                state.isGPDone = true;
            }
            return state.getClustersSimple();
        } else {
            String gps = algorithm.doGraphPartitioning();
            while(!"done".equals(gps)) {
                gps = algorithm.doGraphPartitioning();
            }
            state.isGPDone = true;
            System.out.println(state.toFancyString());
            return state.getClustersSimple();
        }
    }

    /**
     * This method handles running the simulated annealing portion of the algorithm.
     * @return unknown
     */
    @RequestMapping("/runSimulatedAnnealing")
    public SummaryDAO doSimulatedAnnealing() {
        return algorithm.doSimulatedAnnealing().toDAO();
    }

    /**
     * This method handles running the batch processing portion of the algorithm.
     * @return  unknown
     */
    @RequestMapping("/runBatchProcessing")
    public List<Summary> doBatchProcessing(@RequestBody BatchedPreferencesDAO preferenceDAO) {
        if(state == null) {
            state = State.getState(preferenceDAO.getStateName());
        }
        final List<Summary> summaryBatch = new LinkedList<>();
        List<Preference> preferences = preferenceDAO.makePreferences();
        for(Preference preference : preferences) {
            final Algorithm algorithm = new Algorithm(preference, state);
            summaryBatch.add(algorithm.doJob());
        }
        return summaryBatch;
    }

    @RequestMapping("/getBlackDistribution")
    public List<PrecinctAfricanDAO> getBlackDistribution(String stateName) {
        System.out.println(stateName);
        System.out.println();
        if(state == null) {
//            state = State.getState(stateName.getStateName());
            return null;
        }
        LinkedList<PrecinctAfricanDAO> output = new LinkedList<>();

        state.getPrecinctSet().forEach(precinct -> {
//            System.out.println(precinct.getDemographics());
            output.add(new PrecinctAfricanDAO(precinct.getId(),(int)(((double)precinct.getDemographics().getDemographicPopulation().get(DemographicType.AFRICAN_AMERICAN)/(double)precinct.getPopulation()) * 100)));
        });
        return output;
    }
}
