package model;

import java.util.HashMap;

public class Demographics {
    private final HashMap<DemographicType, Integer> demographicPopulation;
    private final HashMap<DemographicType, int[]> demographicVotingData;// the array will be in the format {demVotes, repVotes, other} TODO maybe not

    public Demographics(HashMap<DemographicType, Integer> demographicPopulation, HashMap<DemographicType, int[]> demographicVotingData) {
        this.demographicPopulation = demographicPopulation;
        this.demographicVotingData = demographicVotingData;
    }

    public HashMap<DemographicType, Integer> getDemographicPopulation() {
        return demographicPopulation;
    }

    public HashMap<DemographicType, int[]> getDemographicVotingData() {
        return demographicVotingData;
    }

    public void add(Demographics d) {
        for(DemographicType demoType : DemographicType.values()) {
            //handle demographic population
            demographicPopulation.put(demoType, demographicPopulation.get(demoType) + d.getDemographicPopulation().get(demoType));
            //handle demovoting data
            for(int ii = 0; ii < demographicVotingData.get(demoType).length; ii++) {
                demographicVotingData.get(demoType)[ii] += d.demographicVotingData.get(demoType)[ii];
            }
        }
//        System.out.println("\t\t" + this);
    }

    public void remove(Demographics d) {
        for(DemographicType demoType : DemographicType.values()) {
            //handle demographic population
            demographicPopulation.put(demoType, demographicPopulation.get(demoType) - d.getDemographicPopulation().get(demoType));
            //handle demovoting data
            for(int ii = 0; ii < demographicVotingData.get(demoType).length; ii++) {
                demographicVotingData.get(demoType)[ii] -= d.demographicVotingData.get(demoType)[ii];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(DemographicType dType : DemographicType.values()) {
            sb.append(dType);
            sb.append(" Population:");
            sb.append(demographicPopulation.get(dType));
            sb.append(" voting:");
            for(int vote : demographicVotingData.get(dType)) {
                sb.append(vote);
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static Demographics getDemographicTest() {
        HashMap<DemographicType, Integer> populations = new HashMap<>();
        HashMap<DemographicType, int[]> voting = new HashMap<>();
        int[] vote = {5,3,2};
        int[] vote2 = {10,0,0};
        for(DemographicType type : DemographicType.values()) {
            if(type.equals(DemographicType.WHITE)) {
                populations.put(type, 10);
                voting.put(type,vote2.clone());
            } else {
                populations.put(type, 10);
                voting.put(type, vote.clone());
            }
        }
        Demographics d1 = new Demographics(populations, voting);
        return d1;
    }
}
