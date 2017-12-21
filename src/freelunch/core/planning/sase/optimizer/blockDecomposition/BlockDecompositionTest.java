package freelunch.core.planning.sase.optimizer.blockDecomposition;

import java.io.IOException;

import junit.framework.TestCase;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.SasIO;

public class BlockDecompositionTest extends TestCase {
	
	public void testLoadBlockDecompositionFromFile() throws IOException {
		
		SasProblem prob = SasIO.parse("testfiles/blocks/barman1.sas");
		
		BlockDecomposition bd = new BlockDecomposition(prob, "testfiles/blocks/barman1.bdp");
		System.out.println(bd.getTopLevelBlocks());
	}

}
