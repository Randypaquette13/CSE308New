package cse308.server.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORDHASH")
    private String password;    //actually hashed and salted
    private String firstName;
    private String lastName;
    private boolean admin;

    protected User(){ }

    public User(String initEmail, String initPassword, String initFirstName,
                String initLastName, boolean initAdmin){

        email = initEmail;
        password = initPassword;
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

    @JsonIgnore
    public String getPassword(){
        return password;
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

    @JsonProperty
    public void setPassword(String newPassword){
        password = newPassword;
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
        return String.format("User[email='%s', password='%s', " +
                "firstName='%s', lastName='%s', admin='%b']",
                email, password, firstName, lastName, admin);
    }
}
