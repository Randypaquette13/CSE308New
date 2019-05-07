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

}
