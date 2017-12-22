package freelunch.sat.bce.tests;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;
import freelunch.sat.bce.decomposers.CombinedDecomposer;
import freelunch.sat.bce.decomposers.FormulaDecomposer;
import freelunch.sat.bce.decomposers.PureDecomposer;
import freelunch.sat.bce.decomposers.UnitDecomposer;
import freelunch.sat.bce.decomposers.postprocessors.BlockableClauseMover;
import freelunch.sat.bce.decomposers.postprocessors.CombinedClauseMover;
import freelunch.sat.bce.decomposers.postprocessors.DecompositionPostprocessor;
import freelunch.sat.bce.decomposers.postprocessors.EagerBlockableClauseMover;
import freelunch.sat.bce.eliminators.BCEliminator;
import freelunch.sat.bce.eliminators.IncrementalQueueBasedBCEliminator;
import freelunch.sat.bce.eliminators.SimplifiedArminsBCEliminator;
import freelunch.sat.bce.utilities.FormulaAnalyzer;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import freelunch.sat.solver.Sat4JSolver;

public class DecomposerTests extends TestCase {
	
	/**
	 * Test the following hypothesis:
	 * Let S be a blocked set and C a set of clauses
	 * and let R = BCE(S union C)
	 * then C\(R intersection C) + S is a blocked set
	 */
	public void testHypothesis() {
		String path = "/home/tbalyo/workspace/bcd/testing/flas";
        File benchDir = new File(path);
        File[] files = benchDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith(".cnf");
            }
        });
        Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
        BCEliminator elim = new IncrementalQueueBasedBCEliminator();
        for (File file : files) {
        	BasicFormula f = DimacsParser.parseFromFile(file.getAbsolutePath());
        	//System.out.println("ORIG =========================");
    		//FormulaAnalyzer.analyzeFormula(f).print();
    		FormulaDecomposer dec = new UnitDecomposer();
    		BasicFormula l = new BasicFormula();
    		BasicFormula r = new BasicFormula();
    		dec.decomposeFormula(f, l, r);
    		
    		BasicFormula tmp = new BasicFormula();
    		tmp.variablesCount = l.variablesCount;
    		tmp.clauses = new ArrayList<int[]>();
    		tmp.clauses.addAll(l.clauses);
    		tmp.clauses.addAll(r.clauses);
    		List<int[]> cls = elim.eliminateBlockedClauses(tmp);
    		
    		// what remained
    		tmp.clauses.removeAll(cls);
    		tmp.clauses.retainAll(r.clauses);
    		List<int[]> xx = new ArrayList<int[]>(r.clauses);
    		xx.removeAll(tmp.clauses);
    		System.out.println("added clauses: " + xx.size());
    		if(!EagerBlockableClauseMover.tryAdding(l, xx, 0)) {
    			System.out.println("Hypothesis false");
    			return;
    		}
        }		
	}
	
	public void testOnFiles() {
		String path = "/home/tbalyo/workspace/bcd/testing/flas";
        File benchDir = new File(path);
        File[] files = benchDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith(".cnf");
            }
        });
        Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
        int totalSmall = 0;
        int totalMoved = 0;
        BCEliminator elim = new IncrementalQueueBasedBCEliminator();
        //Logger.setVerbosity(2);
        Stopwatch watch = new Stopwatch();
        int filenum = 0;
        for (File file : files) {
        	BasicFormula f = DimacsParser.parseFromFile(file.getAbsolutePath());
        	//System.out.println("ORIG =========================");
    		//FormulaAnalyzer.analyzeFormula(f).print();
    		FormulaDecomposer dec = new PureDecomposer();
    		BasicFormula l = new BasicFormula();
    		BasicFormula r = new BasicFormula();
    		dec.decomposeFormula(f, l, r);

        	//System.out.println("LARGE =========================");
    		//FormulaAnalyzer.analyzeFormula(l).print();
        	//System.out.println("SMALL =========================");
    		//FormulaAnalyzer.analyzeFormula(r).print();
    		
			int smallSize = r.clauses.size();
			DecompositionPostprocessor postp = new BlockableClauseMover();
			postp.moveToLarge(l, r);
			postp = new EagerBlockableClauseMover();
			postp.moveToLarge(l, r);
			//postp = new OneByOneMover();
			//postp.setTimeLimit(60);
			//postp.moveToLarge(l, r);

			// blockable =     6635 in 28s
			// blocked   =     4885 in 28s
			// eager     =    97078 in 61s
			// bb + eag  =    98586 in 60s
			// combi     =   209682 in 89s
			// bb + cmb  =   220681 in 90s
			// bb+cmb+qd-1 = 249279 in 1731s
			int removed = smallSize - r.clauses.size();
			
        	System.out.print(String.format("(%2d/%d) %30s %5d(%2d%%) of %5d ", ++filenum, files.length, file.getName(),
        			removed, (100*removed)/smallSize, smallSize));
			System.out.println(FormulaAnalyzer.compareFormulas(l, r));

			totalMoved += removed;
			totalSmall += smallSize;
			
			int eliml = elim.eliminateBlockedClauses(l).size();
			assertEquals(l.clauses.size(), eliml);
			
			// count the number of common variables
        }
        System.out.println(String.format("orig small = %d, new small = %d, moved = %d in %s", totalSmall, totalSmall - totalMoved, 
        		totalMoved, watch.elapsedFormatedSeconds()));

	}
	
	public void testDecompositionCorrectness() {
		Logger.setVerbosity(2);
		final int tests = 100;
		final int vars = 150;
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(5000);
		Sat4JSolver solver = new Sat4JSolver();
		int satCount = 0;
		long totalSmallSize = 0;
		SimplifiedArminsBCEliminator elim = new SimplifiedArminsBCEliminator();
		
		for (int i = 0; i < tests; i++) {
			BasicFormula f = rfg.getRandomFormula(vars, 3, 0, 41*vars/10);
			boolean satOrig = solver.isSatisfiable(f);
			
			FormulaDecomposer decomposer = new CombinedDecomposer();
			BasicFormula l = new BasicFormula();
			BasicFormula r = new BasicFormula();
			decomposer.decomposeFormula(f, l, r);
			
			System.out.println(l.clauses.size() + " " + r.clauses.size());
			DecompositionPostprocessor postp = new CombinedClauseMover();
			postp.moveToLarge(l, r);
			
			System.out.println(l.clauses.size() + " " + r.clauses.size());
			totalSmallSize += r.clauses.size();
			
			int eliml = elim.eliminateBlockedClauses(l).size();
			assertEquals(l.clauses.size(), eliml);
			
			// count the number of common variables
			System.out.println(FormulaAnalyzer.compareFormulas(l, r));
			
			
			BasicFormula joined = new BasicFormula();
			joined.variablesCount = f.variablesCount;
			joined.clauses = new ArrayList<int[]>(l.clauses);
			joined.clauses.addAll(r.clauses);
			
			boolean satJoined = solver.isSatisfiable(joined);
			
			//System.out.println(satOrig + " " + satReenc);
			assertEquals(satOrig, satJoined);
			if (satOrig) {
				satCount++;
			}
		}
		System.out.println("SAT: " + satCount);
		System.out.println("small:" + totalSmallSize / tests);
	}
	
	public void testPureDecomposer() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		BasicFormula f = rfg.getRandomFormula(100000, 100000, 400000);
		Stopwatch watch = new Stopwatch();
		
		FormulaDecomposer decomposer = new PureDecomposer();
		decomposer = new UnitDecomposer();
		BasicFormula l = new BasicFormula();
		BasicFormula r = new BasicFormula();
		decomposer.decomposeFormula(f, l, r);
		
		System.out.println(String.format("large: %d (%d%%), small %d", l.clauses.size(), (100*l.clauses.size()/f.clauses.size()), r.clauses.size()));
		System.out.println(watch.elapsedFormatedSeconds());
		// W/O post-processing
		// large: 32536 (65%), small 17464
		// 0.058
		// With post-processing
		// large: 33656 (67%), small 16344
		// 0.091
		
		BCEliminator elim = new SimplifiedArminsBCEliminator();
		int removedl = elim.eliminateBlockedClauses(l).size();
		int removedr = elim.eliminateBlockedClauses(r).size();
		
		assertEquals(l.clauses.size(), removedl);
		assertEquals(r.clauses.size(), removedr);

	}
	
	public void testSatGenParams() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(2013);
		Sat4JSolver solver = new Sat4JSolver();

		for (int vars = 100; vars < 250; vars += 10) {
			int sat = 0;
			for (int i = 0; i < 100; i++) {
				BasicFormula f = rfg.getRandomFormula(vars, (int)(vars*0.03), (int)(vars*0.05), 4*vars);
				if (solver.isSatisfiable(f)) {
					sat++;
				}
			}
			System.out.println(vars + ": " + sat);
		}
	}

}
