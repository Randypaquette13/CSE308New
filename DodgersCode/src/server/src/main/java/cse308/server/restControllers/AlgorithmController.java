package cse308.server.restControllers;

import controller.Algorithm;
import model.Cluster;
import model.Preference;
import model.State;
import model.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class specifies the endpoints and behavior used to run the algorithm.
 */
@RestController
public class AlgorithmController {

    private State state;
    private Algorithm algorithm;

    /**
     * This method handles running the graph partitioning portion of the algorithm.
     * @return  unknown
     */
    @RequestMapping("/runGraphPartitioning")
    public Collection<Cluster> doGraphPartitioning(@RequestBody Preference preference) {
        //TODO: Load state object from DB and set it to the private state obj
        algorithm = new Algorithm(preference, state);

        if (preference.isGraphPartUpdate()) {
            if(state.isGPDone) {
                return null;
            }
            if("done".equals(algorithm.doGraphPartitioning())){
                state.isGPDone = true;
            }
            return state.getClusters();
        } else {
            String gps = algorithm.doGraphPartitioning();
            while(!"done".equals(gps)) {
                gps = algorithm.doGraphPartitioning();
            }
            return state.getClusters();
        }
    }

    /**
     * This method handles running the simulated annealing portion of the algorithm.
     * @return unknown
     */
    @RequestMapping("/runSimulatedAnnealing")
    public Summary doSimulatedAnnealing() {
        return algorithm.doSimulatedAnnealing();
    }

    /**
     * This method handles running the batch processing portion of the algorithm.
     * @return  unknown
     */
    @RequestMapping("/runBatchProcessing")
    public List<Summary> doBatchProcessing(@RequestBody List<Preference> preferences) {
        final List<Summary> summaryBatch = new LinkedList<>();
        for(Preference preference : preferences) {
            final Algorithm algorithm = new Algorithm(preference, state);
            summaryBatch.add(algorithm.doJob());
        }
        return summaryBatch;
    }
}
