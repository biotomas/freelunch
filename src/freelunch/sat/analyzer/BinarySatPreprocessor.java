package freelunch.sat.analyzer;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.GabowStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import freelunch.sat.model.CnfSatFormula;

public class BinarySatPreprocessor {
	
	public void simplify(CnfSatFormula f) {
		System.out.println(f.toString());
		
		Graph<Integer, DefaultEdge> binGraph = new SimpleGraph<>(DefaultEdge.class);
		
		for (int v = 1; v <= f.variablesCount; v++) {
			binGraph.addVertex(v);
			binGraph.addVertex(-v);
		}
		for (int[] cl : f.clauses) {
			if (cl.length == 2) {
				binGraph.addEdge(-cl[0], cl[1]);
				binGraph.addEdge(-cl[1], cl[0]);
			}
		}
		System.out.println(binGraph.toString());
		System.out.println("============");
		
		StrongConnectivityAlgorithm<Integer, DefaultEdge> sccGraph = new GabowStrongConnectivityInspector<>(binGraph);
	
		for (Graph<Integer, DefaultEdge> g : sccGraph.getCondensation().vertexSet()) {
			System.out.println(g.toString());			
		}
		System.out.println("============");
		for (DefaultEdge e : sccGraph.getCondensation().edgeSet()) {
			System.out.println(e.toString());			
		}
		System.out.println("============");
		TopologicalOrderIterator<Graph<Integer, DefaultEdge>, DefaultEdge> top = 
				new TopologicalOrderIterator<>(sccGraph.getCondensation());
		while (top.hasNext()) {
			System.out.println(top.next().toString());
		}
		
		//for each connected component C of the condensation
		// topologically sort C, with DFS check if there is path from x to -x for some x, if yes -> infer x=False
		// and everything after -x is True and before x false
		// if infered something do unitpropagation and repeat?
		
	}

}
