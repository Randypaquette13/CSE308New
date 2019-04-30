package cse308.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    //@Autowired
    //private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean registerNewUser(User newUser){

    return false;


    }

    /*public boolean verifyUser(User potentialUser){
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
    }*/


}
