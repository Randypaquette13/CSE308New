package cse308.server;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String email;
    private String passwordHash;
    private String salt;
    private String sessionCookie;
    private String firstName;
    private String lastName;
    private boolean admin;

    protected User(){ }

    public User(String initEmail, String initPasswordHash, String initSalt, String initSessionCookie,
                String initFirstName, String initLastName, boolean initAdmin){

        email = initEmail;
        passwordHash = initPasswordHash;
        salt = initSalt;
        sessionCookie = initSessionCookie;
        firstName = initFirstName;
        lastName = initLastName;
        admin = initAdmin;
    }

    public String getEmail(){
        return email;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getSalt(){
        return salt;
    }

    public String getSessionCookie(){
        return sessionCookie;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setEmail(String newEmail){
        email = newEmail;
    }

    public void setPasswordHash(String newPasswordHash){
        passwordHash = newPasswordHash;
    }

    public void setSalt(String newSalt){
        salt = newSalt;
    }

    public void setSessionCookie(String newSessionCookie){
        sessionCookie = newSessionCookie;
    }

    public void setFirstName(String newFirstName){
        firstName = newFirstName;
    }

    public void setLastName(String newLastName){
        lastName = newLastName;
    }

    public void setAdmin(boolean newAdmin){
        admin = newAdmin;
    }

    @Override
    public String toString(){
        return String.format("User[email='%s', passwordHash='%s', salt='%s', sessionCookie='%s', " +
                "firstName='%s', lastName='%s', admin='%b']",
                email, passwordHash, salt, sessionCookie, firstName, lastName, admin);
    }
}
