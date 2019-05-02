package cse308.server.restControllers;

import controller.Algorithm;
import model.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class specifies the endpoints and behavior used to run the algorithm.
 */
@RestController
public class AlgorithmController {

    //@Autowired
    //private

    /**
     * This method handles running the graph partitioning portion of the algorithm.
     * @return  unknown
     */
    @RequestMapping("/runGraphPartitioning")
    public String doGraphPartitioning(@RequestBody Preference preference) {
        //Algorithm a = new Algorithm(preference);TODO: How to get the state?
        return "Do Graph Partitioning Here.";
    }

    /**
     * This method handles running the simulated annealing portion of the algorithm.
     * @return unknown
     */
    @RequestMapping("/runSimulatedAnnealing")
    public String doSimulatedAnnealing() {
        return "Do Simulated Annealing Here.";
    }

    /**
     * This method handles running the batch processing portion of the algorithm.
     * @return  unknown
     */
    @RequestMapping("/runBatchProcessing")
    public String doBatchProcessing() {
        return "Do batch Processing here";
        //will likely send messages to both "/runGraphPartitioning" and "/runSimulatedAnnealing"
    }
}
