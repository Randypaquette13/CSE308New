package cse308.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is to be used as a reference for how other controller classes should look.
 */
@RestController
public class TestGreeting {

    private static final String template = "Hello, %s! ";

    /**
     * A test controller mapped to "/greeting". This is to be used as a reference for other controllers if need be.
     *
     * url format: localhost:8080/greeting ? name = ______ & message = _______
     *
     * @param name A name in the query string of the url. If not is specified, will default to "World"
     * @param msg Some message in the query string of the url. If not is specified, will default to "No message
     *            specified"
     * @return A string of the format "Hello, 'name'! 'msg'"
     */
    @RequestMapping("/greeting")    //replace "/greeting" with the endpoint
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name,
                           @RequestParam(value = "message", defaultValue = "No message specified") String msg) {
        return String.format(template, name) + msg;
    }
}
