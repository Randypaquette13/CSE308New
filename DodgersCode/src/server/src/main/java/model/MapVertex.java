package model;

import java.util.List;
import java.util.Set;

public interface MapVertex {
    double[] getDemographicValues();
    Set<Edge> getEdges();
    List<MapVertex> getNeighbors();
}
