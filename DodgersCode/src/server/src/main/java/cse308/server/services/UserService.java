package cse308.server.services;

import cse308.server.EmailAlreadyRegisteredException;
import cse308.server.dao.User;
import cse308.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    public boolean updateUser(User updatedUser, String email){
        System.out.print("Updating user with email: " + email);
        User originalUser = userRepository.findByEmail(email);
        if(originalUser == null){
            return false;
        }
        originalUser.setEmail(updatedUser.getEmail());
        originalUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        originalUser.setFirstName(updatedUser.getFirstName());
        originalUser.setLastName(updatedUser.getLastName());
        originalUser.setAdmin(updatedUser.isAdmin());
        userRepository.save(originalUser);
        return true;
    }


}
