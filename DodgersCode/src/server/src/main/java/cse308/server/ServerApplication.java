package cse308.server;

import controller.Algorithm;
import cse308.server.dao.PreferenceDAO;
import model.State;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The class containing the main method used to start the server.
 */
@SpringBootApplication
public class ServerApplication {

    /**
     * The main method used to start the Spring server
     * @param args arguments
     */
    public static void main(String[] args) {
//        State state = State.getState("New Hampshire");
//        Algorithm algorithm = new Algorithm(new PreferenceDAO(1,1,1,1,1,1,1,0,2,2,false,"New Hampshire").makePreference(),state);
//        String gps = algorithm.doGraphPartitioning();
//        while(!"done".equals(gps)) {
//            gps = algorithm.doGraphPartitioning();
//        }
//        System.out.println(state.getClustersSimple());
//        System.exit(0);
        SpringApplication.run(ServerApplication.class, args);
    }

}
