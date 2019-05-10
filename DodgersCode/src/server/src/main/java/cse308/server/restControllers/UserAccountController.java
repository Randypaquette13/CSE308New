package cse308.server.restControllers;

import cse308.server.EmailAlreadyRegisteredException;
import cse308.server.services.UserService;
import cse308.server.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * This class specifies the endpoints and behavior used to handle users logging in, logging out, and registering.
 */
@RestController
public class UserAccountController {

    @Autowired
    private UserService userService;

    private String s;

    /**
     * This method handles requests from a non-admin user to log a user into the system. MUST ALSO SET THE SESSION
     * COOKIE.
     * @return  Success or Failure, depending on if the username and password exist in the DB or not.
     */
    @SuppressWarnings("Duplicates")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User user, HttpServletRequest req,
                                HttpServletResponse response){
        if(req.getSession(false) != null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);  //400 Response
        }
        System.out.println("New request to log " + user + " in.");
        if(userService.validateUser(user)){
            User verifiedUser = userService.getUser(user.getEmail());
            System.out.println(verifiedUser + " verified.");
            HttpSession session = req.getSession();
            session.setAttribute("id", verifiedUser.getId());
            session.setAttribute("email", verifiedUser.getEmail());
            session.setAttribute("name", verifiedUser.getFirstName());
            session.setAttribute("isAdmin", verifiedUser.isAdmin());
            response.addCookie(new Cookie("name", verifiedUser.getFirstName()));
            return new ResponseEntity(HttpStatus.OK);           //200 Response
        }
        else{
            System.out.println(user + " not verified.");
            return new ResponseEntity(HttpStatus.UNAUTHORIZED); //401 Response
        }
    }

    /**
     * This method handles requests from a non-admin user to log a user out. MUST MARK THE USER'S SESSION COOKIE AS
     * INVALID ON SUCCESS.
     * @return  Success or Failure, depending on if the user was properly logged out.
     */
    @RequestMapping(value = "/loguserout", method = RequestMethod.POST)
    public ResponseEntity logout(/*@RequestBody User u,*/ HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if(session == null){
            System.out.println("No session found, returning 204");
            return new ResponseEntity(HttpStatus.NO_CONTENT);   //204 Response
        }
        User user = new User((String)session.getAttribute("email"),
                null, null ,null, false);
        System.out.println("New request to log " + user + " out.");
        if(userService.exists(user)){
            session.invalidate();
            Cookie[] cookies = req.getCookies();
            for(Cookie c : cookies){
                System.out.println("Invalidating a cookie with value "+ c.getValue());
                c.setMaxAge(0);
            }
            System.out.println(user + " logout successful.");
            return new ResponseEntity(HttpStatus.OK);           //200 Response
        }
        else {
            System.out.println(user + " was not found.");
            return new ResponseEntity(HttpStatus.NO_CONTENT);   //204 Response
        }
    }

    /**
     * This method handles requests from a non-admin user to log out. MUST VALIDATE THE INFO BEFORE REGISTERING IN CASE
     * THERE IS ALREADY A USER WITH THAT USERNAME/EMAIL IN THE SYSTEM.
     * Proper security will have this method receive password hashes instead of plaintext passwords.
     * @return  Success or Failure, depending on if the user was registered successfully or not.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody User user){
        System.out.println("New request to register new " + user);
        try {
            userService.registerNewUser(user);
        }
        catch(EmailAlreadyRegisteredException e){
            System.out.println(user + " user already found in DB.");
            return new ResponseEntity(HttpStatus.CONFLICT); //409 Response
        }
        System.out.println(user + " registered successfully.");
        return new ResponseEntity(HttpStatus.CREATED);    //201 Response
    }

    @RequestMapping(value = "guestlogin", method = RequestMethod.POST)
    public ResponseEntity guestLogin(){
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllUsers")
    public List<User> getAllUsers(){
        System.out.println("Listing all users.");
        List<User> l = userService.getAllUsers();
        return l;
    }


}
