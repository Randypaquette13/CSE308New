package cse308.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
/*
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerNewUser(User newUser) throws EmailAlreadyRegisteredException{
        User user = userRepository.findByEmail(newUser.getEmail());
        if(user == null){
            throw new EmailAlreadyRegisteredException();
        }
        userRepository.save(newUser);
    }

    public boolean verifyUser(User potentialUser){
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

*/
}
