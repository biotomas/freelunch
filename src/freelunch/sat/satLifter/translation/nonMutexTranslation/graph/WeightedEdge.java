package freelunch.sat.satLifter.translation.nonMutexTranslation.graph;

public class WeightedEdge implements Comparable<WeightedEdge> {
    
    private float weight;
    
    // id of from must be always smaller than id of to
    private Vertex from;
    private Vertex to;
    
    public WeightedEdge(Vertex from, Vertex to, float weight) {
        if (from.getId() < to.getId()) {
            this.from = from;
            this.to = to;
        } else {
            this.from = to;
            this.to = from;
        }
        setWeight(weight);
    }
    
    @Override
    public boolean equals(Object obj) {
        WeightedEdge other = (WeightedEdge) obj;
        return (from.getId() == other.from.getId()) && (to.getId() == other.to.getId());
    }
    
    @Override
    public int hashCode() {
        return from.getId() + (to.getId() << 15);
    }
    
    @Override
    public String toString() {
        return String.format("%d->%d(%.3f)", from.getId(), to.getId(), weight);
    }
    
    /**
     * @return the weight
     */
    public float getWeight() {
        return weight;
    }
    /**
     * @param weight the weight to set
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }
    /**
     * @return the from
     */
    public Vertex getFrom() {
        return from;
    }
    /**
     * @return the to
     */
    public Vertex getTo() {
        return to;
    }

    @Override
    public int compareTo(WeightedEdge o) {
        return (int) (o.weight - weight);
    }
}
