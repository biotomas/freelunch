package freelunch.sat.bce.tests;

import java.io.IOException;
import java.util.ArrayList;

import freelunch.sat.bce.encoding.aig.AigEncoder;
import freelunch.sat.bce.encoding.aig.AigerCircuit;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import junit.framework.TestCase;

public class AigTest extends TestCase {
	
	public void testAigTranslation() throws IOException {
		BasicFormula bf = new BasicFormula();
		bf.variablesCount = 3;
		bf.clauses = new ArrayList<int[]>();
		bf.clauses.add(new int[] {-2});
		bf.clauses.add(new int[] {2, -1});
		bf.clauses.add(new int[] {1, 3});
		bf.clauses.add(new int[] {-3});
		
		//bf.clauses.add(new int[] {4,1,2,5,6});
		//bf.clauses.add(new int[] {-1,-4});
		//bf.clauses.add(new int[] {2,-1,-4});
		
		AigEncoder ae = new AigEncoder();
		AigerCircuit ac = ae.encode(bf);

		System.out.println(ac.toString());
		ac.printToAigerFile("outfile.cnf");
	}

}
