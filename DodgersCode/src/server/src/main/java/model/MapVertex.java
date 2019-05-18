package model;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface MapVertex {
    Demographics getDemographics();
    Set<Edge> getEdges();
    List<MapVertex> getNeighbors();
    void addEdgeTo(MapVertex p);
}
