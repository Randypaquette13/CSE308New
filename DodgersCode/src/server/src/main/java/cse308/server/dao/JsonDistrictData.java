package cse308.server.dao;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Entity
@Table(name = "District")
public class JsonDistrictData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "state")
    private String state;

    @Lob
    @Column(name = "data", unique = true)
    private String json;

    //@JsonProperty("newName") over getter

    public JsonDistrictData(String filelocation)throws IOException {
        state = "AZ";
        String contents = new String(Files.readAllBytes(Paths.get(filelocation)));
        System.out.println(contents);
        json = contents;
    }

    public String getJson() {
        return json;
    }

    public String getState(){
        return state;
    }
}
