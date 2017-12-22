package freelunch.sat.solver;

import java.util.Arrays;

import freelunch.sat.satLifter.Stopwatch;
import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.satLifter.tests.RandomFormulaGenerator;
import freelunch.sat.solver.localSearch.BaseWalkSAT;
import freelunch.sat.solver.localSearch.selectors.BSeeker;
import freelunch.sat.solver.localSearch.selectors.FlipFirst;
import freelunch.sat.solver.localSearch.selectors.Gatling;
import freelunch.sat.solver.localSearch.selectors.LocalSearchSelector;
import freelunch.sat.solver.localSearch.selectors.ProbSat;
import freelunch.sat.solver.localSearch.selectors.ProbWalkSat;
import freelunch.sat.solver.localSearch.selectors.Revolver;
import freelunch.sat.solver.localSearch.selectors.Sparrow;
import freelunch.sat.solver.localSearch.selectors.WalkSat;


public class LocalSearchMain {
	
	private static void usage() {
		System.out.println("USAGE:");
		System.out.println(" Generator: java -jar bseeker.jar gen <vars> <seed>");
		System.out.println(" Solver: java -jar bseeker.jar <formula.cnf> [algorithm] [timelimit]");
		System.out.println(" Algorihtms: " + Arrays.toString(Selectors.values()));
	}
	
	public static enum Selectors {
		sparrow, walksat, bseeker, flipfirst, probsat, gatling, revolver, probwalksat;
	}

	public static void main(String[] args) {
		System.out.println("c BSeeker local search SAT solver and formula generator version 0.71");
		if (args.length < 1) {
			usage();
			return;
		}
		
		if (args[0].equals("gen")) {
			RandomFormulaGenerator rfg = new RandomFormulaGenerator(Long.parseLong(args[2]));
			int vars = Integer.parseInt(args[1]);
			rfg.getRandomFormula(vars, (int)(vars*0.03), (int)(vars*0.05), 4*vars).printDimacs(System.out);
			return;
		}
		
		Stopwatch watch = new Stopwatch();
		BasicFormula f = DimacsParser.parseFromFile(args[0]);
		
		LocalSearchSelector selector = null;
		String alg = "bseeker";
		if (args.length > 1) {
			alg = args[1];
		}
		switch (Selectors.valueOf(alg)) {
			case sparrow:
				selector = new Sparrow();
				break;
			case walksat:
				selector = new WalkSat();
				break;
			case bseeker:
				selector = new BSeeker();
				break;
			case flipfirst:
				selector = new FlipFirst();
				break;
			case probsat:
				selector = new ProbSat();
				break;
			case probwalksat:
				selector = new ProbWalkSat();
				break;
			case gatling:
				selector = new Gatling();
				break;
			case revolver:
				selector = new Revolver();
				break;
		}
		long timelimit = 0;
		if (args.length > 2) {
			timelimit = Long.parseLong(args[2]);
		}
		String sname = selector.getClass().getCanonicalName().replace("satSolver.localSearch.selectors.", "");
		System.out.println(String.format("c running %s for %d seconds", sname, timelimit));
		LocalSearchSatSolver solver = new BaseWalkSAT(selector, 2013);
		solver.setTimeout(timelimit*1000000000);
		Boolean sat = solver.isSatisfiable(f);
		System.out.println(String.format("c %s time %s flips %d fps %.0f restarts %d", sname, watch.elapsedFormatedSeconds(), 
				solver.getFlipsCount(), solver.getFlipsCount()/watch.elapsedSecondsFloat(),	solver.getRestartsCount()));
		
		if (sat != null && !solutionValid(f, solver.getModel())) {
			System.err.println("INVALID SOLUTION");
		}
		if (sat != null) {
			System.out.println("s SATISFIABLE " + sname);
		} else {
			System.out.println("s UNKNOWN " + sname);
		}
		if (selector instanceof Revolver) {
			((Revolver)selector).printStatus();
		}
	}
	
	public static boolean solutionValid(BasicFormula f, int[] model) {
		clauses:
		for (int[] cl : f.clauses) {
			for (int lit : cl) {
				int var = Math.abs(lit);
				if (model[var] == lit) {
					continue clauses;
				}
			}
			return false;
		}
		return true;
	}

}
