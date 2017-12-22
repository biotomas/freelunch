package freelunch.sat.satLifter.translation.nonMutexTranslation.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex {
    
    private List<Integer> associatedVariables;
    private Set<WeightedEdge> incidentEdges;
    private int id;
    
    public Vertex(int variableId) {
        this.id = variableId;
        setIncidentEdges(new HashSet<WeightedEdge>());
        setAssociatedVariables(new ArrayList<Integer>());
        associatedVariables.add(variableId);
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return associatedVariables.toString();
    }

    /**
     * @return the incidentEdges
     */
    public Set<WeightedEdge> getIncidentEdges() {
        return incidentEdges;
    }

    /**
     * @param incidentEdges the incidentEdges to set
     */
    public void setIncidentEdges(Set<WeightedEdge> incidentEdges) {
        this.incidentEdges = incidentEdges;
    }

    /**
     * @return the associatedVariables
     */
    public List<Integer> getAssociatedVariables() {
        return associatedVariables;
    }

    /**
     * @param associatedVariables the associatedVariables to set
     */
    public void setAssociatedVariables(List<Integer> associatedVariables) {
        this.associatedVariables = associatedVariables;
    }

}
