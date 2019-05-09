package cse308.server.restControllers;

import cse308.server.dao.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public ResponseEntity updateUser(@RequestBody User userToUpdate, HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session == null || !session.getAttribute("isAdmin").equals("true")){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return null;
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
