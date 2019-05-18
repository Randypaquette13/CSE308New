package cse308.server.dao;

public class StringDAO {
    String stateName;
    String stateName2;

    public StringDAO(String stateName, String stateName2) {
        this.stateName = stateName;
        this.stateName2 = stateName2;
    }

    protected StringDAO() {}


    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName2() {
        return stateName2;
    }

    public void setStateName2(String stateName2) {
        this.stateName2 = stateName2;
    }
}
