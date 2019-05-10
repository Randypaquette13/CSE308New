package cse308.server.services;

import cse308.server.EmailAlreadyRegisteredException;
import cse308.server.dao.User;
import cse308.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerNewUser(User newUser) throws EmailAlreadyRegisteredException {
        System.out.println("Attempting to register " + newUser);
        User user = userRepository.findByEmail(newUser.getEmail());
        if(user != null){
            throw new EmailAlreadyRegisteredException();
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

    public boolean validateUser(User potentialUser){
        System.out.println("Attempting to verify " + potentialUser);
        User user = userRepository.findByEmail(potentialUser.getEmail());
        if(user == null){
            return false;
        }
        if(passwordEncoder.matches(potentialUser.getPassword(), user.getPassword())){
            return true;
        }
        else{
            return false;
        }
    }

    public List<User> getAllUsers(){
        System.out.println("Searching for all users.");
        return userRepository.findAll();
    }

    public boolean exists(User potentialUser){
        User user = userRepository.findByEmail(potentialUser.getEmail());
        if(user == null){
            return false;
        }
        else return true;
    }

    public User getUser(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Takes a reference to a user that was found in the database and updates it.
     * @param updatedUser A User object that has the updated information from the user.
     * @param oldUserInfo A User object that was found from the database. Contains the
     *                    old information. MUST HAVE THE ID.
     * @return
     */
    public void updateUser(User updatedUser, User oldUserInfo){
        System.out.print("Updating " + oldUserInfo);
        if(updatedUser.getEmail() != null) {
            oldUserInfo.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getPassword() != null) {
            oldUserInfo.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if(updatedUser.getFirstName() != null) {
            oldUserInfo.setFirstName(updatedUser.getFirstName());
        }
        if(updatedUser.getLastName() != null) {
            oldUserInfo.setLastName(updatedUser.getLastName());
        }
        if(oldUserInfo.isAdmin() != updatedUser.isAdmin()){
            oldUserInfo.setAdmin(updatedUser.isAdmin());
        }
        System.out.println("New info: " + oldUserInfo);
        userRepository.save(oldUserInfo);
    }

    /**
     * Searches for a user in the DB by their id
     * @param id The id of the user we wish to find
     * @return The User, if it exists in the DB, else null
     */
    public User findById(Long id){
        Optional<User> option = userRepository.findById(id);
        if(option.isPresent()){
            return option.get();
        }
        return null;
    }


}
