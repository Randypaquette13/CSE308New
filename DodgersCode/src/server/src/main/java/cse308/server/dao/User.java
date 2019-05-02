package cse308.server.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;    //actually hashed and salted
    private String sessionCookie;
    private String firstName;
    private String lastName;
    private boolean admin;

    protected User(){ }

    public User(String initEmail, String initPassword, String initSessionCookie,
                String initFirstName, String initLastName, boolean initAdmin){

        email = initEmail;
        password = initPassword;
        sessionCookie = initSessionCookie;
        firstName = initFirstName;
        lastName = initLastName;
        admin = initAdmin;
    }

    public Long getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
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

    public void setId(Long newId){
        id = newId;
    }

    public void setEmail(String newEmail){
        email = newEmail;
    }

    public void setPassword(String newPassword){
        password = newPassword;
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
        return String.format("User[email='%s', password='%s', sessionCookie='%s', " +
                "firstName='%s', lastName='%s', admin='%b']",
                email, password, sessionCookie, firstName, lastName, admin);
    }
}
