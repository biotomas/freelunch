package freelunch.sat.satLifter.tests;

import freelunch.sat.satLifter.translation.nonMutexTranslation.graph.InteractionGraph;
import freelunch.sat.satLifter.translation.nonMutexTranslation.graph.WeightedEdge;
import junit.framework.TestCase;

public class InteractionGraphTest extends TestCase {
    
    public void testInteractionGraph() {
        InteractionGraph g = new InteractionGraph(10);
        g.addClause(new int[] {1,2,5});
        //g.addClause(new int[] {6,7,5,2});
        g.addClause(new int[] {7,8,9,10});
        g.addClause(new int[] {10,2,5});
        //g.addClause(new int[] {4,2,7});
        g.addClause(new int[] {3,4});
        System.out.println(g.getEdges());
        
        WeightedEdge e = g.getHeaviesEdge();
        System.out.println(e);
        g.contractEdge(e);
        System.out.println(g.getEdges());
        e = g.getHeaviesEdge();
        System.out.println(e);
        g.contractEdge(e);
        System.out.println(g.getEdges());
        e = g.getHeaviesEdge();
        System.out.println(e);
        g.contractEdge(e);
        System.out.println(g.getVertices());
        System.out.println(g.getEdges());
    }
    
    public void testPrint() {
        InteractionGraph g = new InteractionGraph(10);
        g.addClause(new int[] {1,2,5});
        //g.addClause(new int[] {6,7,5,2});
        //g.addClause(new int[] {7,8,9,10});
        g.addClause(new int[] {10,2,5});
        //g.addClause(new int[] {4,2,7});
        g.addClause(new int[] {3,4,6});
        
        g.printGraphUnweighted(System.out);
    	
    }

}
