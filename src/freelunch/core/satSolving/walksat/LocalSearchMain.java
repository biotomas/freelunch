package freelunch.core.satSolving.walksat;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.satSolving.SatSolver;
import freelunch.core.satSolving.walksat.BaseWalkSAT.BaseWalkSatSettings;
import freelunch.core.utilities.Stopwatch;

public class LocalSearchMain {

	public static void main(String[] args) throws TimeoutException {
		
		if (args.length < 1) {
			System.out.println("USAGE java -jar ff.jar <formula.cnf> [sparrow | ff | walksat]");
			return;
		}
		
		Stopwatch watch = new Stopwatch();
		BasicSatFormula f = BasicSatFormula.parseFromFile(args[0]);
		
		BaseWalkSatSettings settings = new BaseWalkSatSettings();
		settings.setRestartsToImprove(Integer.MAX_VALUE);
		SatSolver solver = new FlipFirst(settings);
		
		if (args.length > 1) {
			if (args[1].equals("walksat")) {
				solver = new WalkSat(settings);
			}
			if (args[1].equals("sparrow")) {
				solver = new Sparrow();
			}
		}
		System.out.println("c running " + solver.getClass().getCanonicalName());
		boolean sat = solver.isSatisfiable(f);
		System.out.println("c CPU Time " + watch.elapsedFormatedSeconds());
		
		if (!solutionValid(f, solver.getModel())) {
			System.err.println("INVALID SOLUTION");
		}
		
		if (sat) {
			System.out.println("s SATISFIABLE");
		} else {
			System.out.println("s UNKNOWN");
		}

	}
	
	public static boolean solutionValid(BasicSatFormula f, int[] model) {
		clauses:
		for (int[] cl : f.getClauses()) {
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
