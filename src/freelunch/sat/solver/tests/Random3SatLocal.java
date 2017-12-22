package freelunch.sat.solver.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import freelunch.sat.solver.LocalSearchMain;
import freelunch.sat.solver.LocalSearchSatSolver;
import freelunch.sat.solver.Sat4JSolver;
import freelunch.sat.solver.SatSolver;
import freelunch.sat.solver.localSearch.BaseWalkSAT;
import freelunch.sat.solver.localSearch.hitandwalk.HitAndWalkSolver;
import freelunch.sat.solver.localSearch.selectors.ProbSat;
import freelunch.sat.solver.refutation.ResolutionRefutation;
import junit.framework.TestCase;

public class Random3SatLocal extends TestCase {
	
	public void testOnFiles() {
		String path = "/home/tbalyo/workspace/bcd/data/myrandom/out";
        File benchDir = new File(path);
        File[] files = benchDir.listFiles();
        Arrays.sort(files);
        // with stack:      queue	
        // probsat 19.406	3.978
        // walksat 4.339	4.995
        // sparrow 28.248	19.75
        // bseeker 150.43	0.896
        // gatling 107.00	7.394
        // revolve 10.867	2.350
		LocalSearchSatSolver local = new BaseWalkSAT(new ProbSat(), 2013);
		local.setTimeout(5000000000l); //5s
		Stopwatch totalTime = new Stopwatch();
		totalTime.pause();
		int problemNr = 1;
		int timeouts = 0;
		float totalFps = 0;
		long totalFlips = 0;
        for (File file : files) {
        	System.out.print(String.format("(%3d/%d) ", problemNr++, files.length));
        	BasicFormula f = DimacsParser.parseFromFile(file.getAbsolutePath());
        	//BasicFormula f = DimacsParser.parseFromFile("/home/tbalyo/big3sat.cnf");
			Stopwatch timer = new Stopwatch();
			totalTime.unpause();
			Boolean res = local.isSatisfiable(f);
			totalTime.pause();
			float fps = local.getFlipsCount()/timer.elapsedSecondsFloat();
			totalFps += fps;
			totalFlips += local.getFlipsCount();
			System.out.print(timer.elapsedFormatedSeconds());
			System.out.print(String.format(" restarts: %3d flips: %12d fps: %.3f", local.getRestartsCount(), 
					local.getFlipsCount(), fps));
			if (res == null) {
				System.out.print(" TIMEOUT");
				timeouts++;
			}
			System.out.println();
			if (res != null && !LocalSearchMain.solutionValid(f, local.getModel())) {
				System.err.println("Invalid Solution");
				return;
			}
        }
		System.out.println(String.format("time: %s timeouts %d (of %d) avg. fps %f flips %d", 
				totalTime.elapsedFormatedSeconds(), timeouts, files.length, totalFps/files.length, totalFlips));
	}
	
	public void testQudraticUnsat() {
		BasicFormula f = new BasicFormula();
		f.variablesCount = 2;
		f.clauses = new ArrayList<int[]>();
		f.clauses.add(new int[] {1,2});
		f.clauses.add(new int[] {-1,2});
		f.clauses.add(new int[] {1,-2});
		f.clauses.add(new int[] {-1,-2});
		SatSolver s = new ResolutionRefutation();
		boolean res = s.isSatisfiable(f);
		System.out.println(res);
	}

	public void testRefutationOnRandom() {
		int vars = 20;
		int clauses = vars*4 + vars/4;
		int tests = 100;
		
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(5000);
		Sat4JSolver sat4j = new Sat4JSolver();
		SatSolver local = new ResolutionRefutation();
		Stopwatch totalTime = new Stopwatch();
		totalTime.pause();
		while (tests > 0) {
			BasicFormula f = rfg.getRandomFormula(vars, 0, clauses);
			boolean s4jres = sat4j.isSatisfiable(f);
			if (s4jres) {
				continue;
			}
			tests--;
			
			BasicFormula f2 = f.copy();
			
			ResolutionRefutation.extendAllVariables(f2, 1);

			Stopwatch timer = new Stopwatch();
			totalTime.unpause();
			boolean res = local.isSatisfiable(f);
			System.out.println("------");
			boolean res2 = false;// local.isSatisfiable(f2);
			totalTime.pause();
			System.out.println(timer.elapsedFormatedSeconds());
			if (s4jres != res || res2 != res) {
				System.err.println("BAD RESULT");
			}
		}
		System.out.println("Total time: " + totalTime.elapsedFormatedSeconds());
	}
	
	public void testHitAndWalk() {
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(5000);
		BasicFormula f = rfg.getRandomSatisfiableFormula(100, 430);
		SatSolver s = new HitAndWalkSolver(2017);
		System.out.println(s.isSatisfiable(f));
	}

	public void testLocalSearchPerformance() throws IOException {
		int vars = 300;
		int clauses = (int) (vars*4.3);
		int tests = 100;
		
		RandomFormulaGenerator rfg = new RandomFormulaGenerator(5000);
		// Sparrow slooooow
		// WalkSat 2.909
		// ProbSat 18.966
		//SatSolver local = new BaseWalkSAT(new WalkSat(), 2013);
		SatSolver local = new HitAndWalkSolver(2017);
		Stopwatch totalTime = new Stopwatch();
		totalTime.pause();
		while (tests > 0) {
			BasicFormula f = rfg.getRandomSatisfiableFormula(vars, clauses);
			//FileWriter fw = new FileWriter("/home/balyo/tmp/rsat/rnd"+tests+".cnf");
			//f.printDimacsToFile(fw);
			//fw.close();
			tests--;
			Stopwatch timer = new Stopwatch();
			totalTime.unpause();
			local.isSatisfiable(f);
			totalTime.pause();
			System.out.println(tests + ": "+timer.elapsedFormatedSeconds());
			if (!LocalSearchMain.solutionValid(f, local.getModel())) {
				System.err.println("Invalid Solution");
				return;
			}
		}
		System.out.println("Total time: " + totalTime.elapsedFormatedSeconds());
	}

}
