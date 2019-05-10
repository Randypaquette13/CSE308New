package cse308.server.restControllers;

import cse308.server.EmailAlreadyRegisteredException;
import cse308.server.dao.User;
import cse308.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class specifies the endpoints and behavior used by an admin user to manage other user accounts.
 */
@SuppressWarnings("Duplicates")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * This method handles requests from an admin to update a user.
     * @return  Success or Failure, depending on if the user was updated successfully or not.
     */
    @RequestMapping(value = "/updateuser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User updatedUserInfo, HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(!verifyAdmin(req)){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);     //401 Response, not admin
        }
        User oldUserInfo = userService.findById(updatedUserInfo.getId());
        if(oldUserInfo == null){
            System.out.println("User " + updatedUserInfo + " not found in DB.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);      //400 Response, user didn't exist
        }
        else{
            System.out.println("Updating user from " + oldUserInfo + " to " + updatedUserInfo);
            userService.updateUser(updatedUserInfo, oldUserInfo);
        }
        if(updatedUserInfo.getId() == session.getAttribute("id")){
            System.out.println("User wants to modify own info, update session and cookie");
            session.setAttribute("email", updatedUserInfo.getEmail());
            session.setAttribute("name", updatedUserInfo.getFirstName());
            session.setAttribute("isAdmin", updatedUserInfo.isAdmin());
            Cookie[] cookies = req.getCookies();
            for(Cookie c : cookies){
                if(c.getName().equals("name")) {
                    System.out.println("Updating cookie with name " + c.getValue());
                    c.setValue(updatedUserInfo.getFirstName());
                }
            }
        }
        return new ResponseEntity(HttpStatus.OK);           //200 Response
    }

    /**
     * This method handles requests from an admin to delete a user.
     * @return  Success or Failure, depending on if the user was deleted successfully or not.
     */
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public ResponseEntity deleteUser(@RequestBody User userToDelete, HttpServletRequest req) {
        System.out.println("New request to delete " + userToDelete);
        if(!verifyAdmin(req)){
            System.out.println("Submitting person isn't an admin");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);         //401 Response, user isn't an admin.
        }
        if(userToDelete.getId() == null || userToDelete.getId() <= 0){
            System.out.println(userToDelete + " had bad id.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);          //400 Response, bad data sent
        }
        userService.deleteUser(userToDelete);
        return new ResponseEntity(HttpStatus.OK);                       //200 Response
    }

    /**
     * This method handles requests from an admin to register a new user.
     * @return  Success or Failure, depending on if the user was registered successfully or not.
     */
    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody User userToRegister, HttpServletRequest req){
        System.out.println("New request to register " + userToRegister);
        if(!verifyAdmin(req)){
            System.out.println("Submitting person isn't an admin.");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);         //401 Response, user isn't an admin.
        }
        if(!validateUserInfo(userToRegister)){
            System.out.println(userToRegister + " had bad or missing data.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);          //400 Response, bad data sent
        }
        try{
            userService.registerNewUser(userToRegister);
        }
        catch(EmailAlreadyRegisteredException e){
            System.out.println(userToRegister + " uses email already found in system.");
            return new ResponseEntity(HttpStatus.CONFLICT);             //409 Response, email already registered
        }
        System.out.println(userToRegister + " successfully registered.");
        return new ResponseEntity(HttpStatus.CREATED);                  //201 Response
    }

    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public ResponseEntity adminLogin(@RequestBody User user, HttpServletRequest req,
                                     HttpServletResponse response){
        if(req.getSession(false) != null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);  //400 Response, already logged in
        }
        if(userService.validateUser(user)){
            User adminUser = userService.getUser(user.getEmail());
            System.out.println(user + " verified.");
            if(!adminUser.isAdmin()){
                System.out.println(user + " was not an admin.");
                return new ResponseEntity(HttpStatus.UNAUTHORIZED); //401 Response
            }
            HttpSession session = req.getSession();
            session.setAttribute("id", adminUser.getId());
            session.setAttribute("email", adminUser.getEmail());
            session.setAttribute("name", adminUser.getFirstName());
            session.setAttribute("isAdmin", adminUser.isAdmin());
            response.addCookie(new Cookie("name", adminUser.getFirstName()));
            return new ResponseEntity(HttpStatus.OK);           //200 Response
        }
        else{
            System.out.println(user + " not verified.");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //401 Response
        }

    }

    public boolean verifyAdmin(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session == null){
            System.out.println("No session.");
            return false;
        }
        boolean isAdmin = (boolean)session.getAttribute("isAdmin");
        if(!isAdmin){
            System.out.println("Not an admin.");
            return false;
        }
        System.out.println("Is an admin.");
        return true;
    }

    public boolean validateUserInfo(User userToValidate){
        if(userToValidate.getEmail() == null || userToValidate.getEmail().equals("") ||
                userToValidate.getPassword() == null || userToValidate.getPassword().equals("") ||
                userToValidate.getFirstName() == null || userToValidate.getFirstName().equals("") ||
                userToValidate.getLastName() == null || userToValidate.getLastName().equals("")){
            return false;
        }
        return true;
    }

}
