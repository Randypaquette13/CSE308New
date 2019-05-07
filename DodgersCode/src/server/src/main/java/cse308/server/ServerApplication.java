package cse308.server;

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
        SpringApplication.run(ServerApplication.class, args);
    }

}
