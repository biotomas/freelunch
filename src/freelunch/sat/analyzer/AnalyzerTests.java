package freelunch.sat.analyzer;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import freelunch.planning.TimeoutException;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.Sat4JSolver;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import junit.framework.TestCase;

public class AnalyzerTests extends TestCase {
	
	public void testBinarySatPreprocessor() throws TimeoutException {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(5);
		CnfSatFormula f = rfg.getRandomFormula(5, 7, 0);
		BinarySatPreprocessor bsp = new BinarySatPreprocessor();
		bsp.simplify(f);
		Sat4JSolver s = new Sat4JSolver();
		if(s.isSatisfiable(f)) {
			System.out.println(Arrays.toString(s.getModel()));			
		} else {
			System.out.println("UNSAT");
		}
		
	}
	
	public void testHittingSetSolver() {
		HittingSetSolver hss = new HittingSetSolver();
		hss.addSet(Arrays.asList(1, 2, 3), 1);
		hss.addSet(Arrays.asList(2, 3), 2);
		hss.addSet(Arrays.asList(3, 4, 5), 1);
		hss.addSet(Arrays.asList(5, 6, 7), 1);
		System.out.println(hss.computeHittingSet());
	}
	
	public void testHornAnalyzerRandom3Sat() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(1);
		CnfSatFormula f = rfg.getRandomFormula(10, 0, 42);
		Stopwatch watch = new Stopwatch();
		List<Integer> bd = HornAnalyzer.getHornBackDoorVariables(f);
		System.out.println(watch.elapsedFormatedSeconds());
		System.out.println(bd.size());
	}
	
	public void testHiddenHornMaximizer() {
		Logger.setVerbosity(2);
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(1);
		CnfSatFormula f = rfg.getRandomFormula(100000, 0, 425000);
		//System.out.println(f);
		HornAnalyzer.getHornBackDoorVariables(f);
		HiddenHornMaximizer hhm = new HiddenHornMaximizer();
		BitSet flips = hhm.hornify(f, 100);
		System.out.println(flips.cardinality());
		//System.out.println(f);
		HornAnalyzer.getHornBackDoorVariables(f);
	}
	
	public void testHornAdvisor() {
		Logger.setVerbosity(2);
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(1);
		//BasicFormula f = rfg.getRandomSat(10, 0, 20);
		CnfSatFormula f = rfg.getRandomFormula(100000, 0, 425000);

		HornAdvisor ha = new HornAdvisor();
		List<Integer> advice = ha.preprocessFormula(f, 20);
		System.out.println(advice);
		Logger.print(0, "done");
	}

}
