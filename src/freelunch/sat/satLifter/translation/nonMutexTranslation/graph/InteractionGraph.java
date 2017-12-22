package freelunch.sat.satLifter.translation.nonMutexTranslation.graph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class InteractionGraph {
    
    private class EdgeId {
        private int vertex1Id;
        private int vertex2Id;
        
        private EdgeId(int id1, int id2) {
            this.vertex1Id = Math.min(id1, id2);
            this.vertex2Id = Math.max(id1, id2);
        }
        @Override
        public boolean equals(Object obj) {
            EdgeId o = (EdgeId) obj;
            return (vertex1Id == o.vertex1Id && vertex2Id == o.vertex2Id);
        }
        @Override
        public int hashCode() {
            return vertex1Id + (vertex2Id << 15);
        }
        @Override
        public String toString() {
            return String.format("%d-%d", vertex1Id, vertex2Id);
        }
    }
    
    private Vertex[] vertices;
    private Map<EdgeId, WeightedEdge> edges;
    
    private SortedMap<Float, Set<WeightedEdge>> edgesQueue;
    
    public InteractionGraph(int variables) {
        vertices = new Vertex[variables+1];
        for (int i = 1; i <= variables; i++) {
            vertices[i] = new Vertex(i);
        }
        edges = new HashMap<EdgeId, WeightedEdge>();
        edgesQueue = new TreeMap<Float, Set<WeightedEdge>>();
    }
    
    public void addClause(int[] clause) {
        float weight = 1f / (clause.length * clause.length);
        for (int i = 0; i < clause.length; i++) {
            int var1 = Math.abs(clause[i]);
            for (int j = i+1; j < clause.length; j++) {
                int var2 = Math.abs(clause[j]);
                Vertex v1 = vertices[var1];
                Vertex v2 = vertices[var2];
                addEdge(v1, v2, weight);
            }
        }
    }
    
    public WeightedEdge getHeaviesEdge() {
        return edgesQueue.get(edgesQueue.lastKey()).iterator().next();
    }
    
    public void addEdge(Vertex from, Vertex to, float weight) {
        EdgeId eid = new EdgeId(from.getId(), to.getId());
        if (edges.containsKey(eid)) {
            // update edge weight
            WeightedEdge tmp = edges.get(eid);
            removeEdgeFromQueue(tmp);
            tmp.setWeight(weight + tmp.getWeight());
            addEdgeToQueue(tmp);
        } else {
            // create new edge
            WeightedEdge newEdge = new WeightedEdge(from, to, weight); 
            edges.put(eid, newEdge);
            addEdgeToQueue(newEdge);
            from.getIncidentEdges().add(newEdge);
            to.getIncidentEdges().add(newEdge);
        }
    }
    
    public List<Vertex> getVertices() {
        List<Vertex> result = new ArrayList<Vertex>();
        for (Vertex v : vertices) {
            if (v != null) {
                result.add(v);
            }
        }
        return result;
    }
    
    public void removeEdge(WeightedEdge e) {
        EdgeId eid = new EdgeId(e.getFrom().getId(), e.getTo().getId());
        edges.remove(eid);
        removeEdgeFromQueue(e);
        e.getFrom().getIncidentEdges().remove(e);
        e.getTo().getIncidentEdges().remove(e);
    }
    
    private void removeEdgeFromQueue(WeightedEdge e) {
        edgesQueue.get(e.getWeight()).remove(e);
        if (edgesQueue.get(e.getWeight()).isEmpty()) {
            edgesQueue.remove(e.getWeight());
        }
    }
    
    private void addEdgeToQueue(WeightedEdge e) {
        if (!edgesQueue.containsKey(e.getWeight())) {
            edgesQueue.put(e.getWeight(), new HashSet<WeightedEdge>());
        }
        edgesQueue.get(e.getWeight()).add(e);
    }
    
    public Collection<WeightedEdge> getEdges() {
        return edges.values();
    }
    
    public void removeVertex(Vertex vertex) {
        vertices[vertex.getId()] = null;
    }
    
    public void contractEdge(WeightedEdge edge) {
        Vertex v1 = edge.getFrom();
        Vertex v2 = edge.getTo();
        
        if (v1.getIncidentEdges().size() < v2.getIncidentEdges().size()) {
            // swap to make v2 have fewer edges
            v1 = edge.getTo();
            v2 = edge.getFrom();
        }
        v1.getAssociatedVariables().addAll(v2.getAssociatedVariables());
        // we will remove v2 and connect its edges to v1
        List<WeightedEdge> edgesToRemove = new ArrayList<WeightedEdge>();
        edgesToRemove.add(edge);
        for (WeightedEdge e : v2.getIncidentEdges()) {
            if (e == edge) {
                continue;
            }
            // neighbor of v2
            Vertex neighbor = e.getFrom();
            if (neighbor == v2) {
                neighbor = e.getTo();
            }
            edgesToRemove.add(e);
            addEdge(neighbor, v1, e.getWeight());
        }
        removeVertex(v2);
        for (WeightedEdge e : edgesToRemove) {
            removeEdge(e);
        }
    }
    
    public void printGraphUnweighted(PrintStream out) {
    	out.println((vertices.length-1) + " " + edges.size());
    	for (int vid = 1; vid < vertices.length; vid++) {
    		//out.print(vid + ": ");
    		for (WeightedEdge e : vertices[vid].getIncidentEdges()) {
    			int v1 = e.getTo().getId();
    			int v2 = e.getFrom().getId();
    			int v = v1==vid ? v2 : v1;
    			out.print(v + " ");
    		}
    		out.println();
    	}
    }
    
}
