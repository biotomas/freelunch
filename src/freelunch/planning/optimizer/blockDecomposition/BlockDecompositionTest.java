package freelunch.planning.optimizer.blockDecomposition;

import java.io.IOException;

import freelunch.planning.model.SasIO;
import freelunch.planning.model.SasProblem;
import junit.framework.TestCase;

public class BlockDecompositionTest extends TestCase {
	
	public void testLoadBlockDecompositionFromFile() throws IOException {
		
		SasProblem prob = SasIO.parse("testfiles/blocks/barman1.sas");
		
		BlockDecomposition bd = new BlockDecomposition(prob, "testfiles/blocks/barman1.bdp");
		System.out.println(bd.getTopLevelBlocks());
	}

}
