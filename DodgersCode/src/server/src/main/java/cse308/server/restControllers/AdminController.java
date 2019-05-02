package cse308.server.restControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class specifies the endpoints and behavior used by an admin user to manage other user accounts.
 */
@RestController
public class AdminController {

    /**
     * This method handles requests from an admin to update a user.
     * @return  Success or Failure, depending on if the user was updated successfully or not.
     */
    @RequestMapping("/updateUser")
    public String updateUser(){
        return "Admin updating a user goes here.";
    }

    /**
     * This method handles requests from an admin to delete a user.
     * @return  Success or Failure, depending on if the user was deleted successfully or not.
     */
    @RequestMapping("/deleteUser")
    public String deleteUser() {
        return "Admin deleting a user goes here.";
    }

    /**
     * This method handles requests from an admin to register a new user.
     * @return  Success or Failure, depending on if the user was registered successfully or not.
     */
    @RequestMapping("/registerUser")
    public String registerUser(){
        return "Admin registering a user goes here.";
        //will likely end up sending a POST request to /register
    }

}
