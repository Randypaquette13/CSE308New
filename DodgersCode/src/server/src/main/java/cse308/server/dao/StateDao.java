package cse308.server.dao;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import model.State;

@Entity
public class StateDao {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private State state;
    private Long userId;

    protected StateDao(){}

    public StateDao(State initState, Long initUserId){
        state = initState;
        userId = initUserId;
    }

    public Long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

