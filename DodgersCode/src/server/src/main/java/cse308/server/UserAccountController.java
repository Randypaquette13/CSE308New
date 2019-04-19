package cse308.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class specifies the endpoints and behavior used to handle users logging in, logging out, and registering.
 */
@RestController
public class UserAccountController {

    /**
     * This method handles requests from a non-admin user to log a user into the system. MUST ALSO SET THE SESSION
     * COOKIE.
     * @return  Success or Failure, depending on if the username and password exist in the DB or not.
     */
    @RequestMapping("/login")
    public String login(){
        return "Login goes here.";
    }

    /**
     * This method handles requests from a non-admin user to log a user out. MUST MARK THE USER'S SESSION COOKIE AS
     * INVALID ON SUCCESS.
     * @return  Success or Failure, depending on if the user was properly logged out.
     */
    @RequestMapping("/logout")
    public String logout(){
        return "Logout goes here.";
    }

    /**
     * This method handles requests from a non-admin user to log out. MUST VALIDATE THE INFO BEFORE REGISTERING IN CASE
     * THERE IS ALREADY A USER WITH THAT USERNAME/EMAIL IN THE SYSTEM.
     * Proper security will have this method receive password hashes instead of plaintext passwords.
     * @return  Success or Failure, depending on if the user was registered successfully or not.
     */
    @RequestMapping("/register")
    public String register(){
        return "Register goes here.";
    }
}
