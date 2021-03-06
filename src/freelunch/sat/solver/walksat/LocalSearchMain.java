package freelunch.sat.solver.walksat;

import freelunch.planning.model.TimeoutException;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.SatSolver;
import freelunch.sat.solver.walksat.BaseWalkSAT.BaseWalkSatSettings;
import freelunch.utilities.Stopwatch;

public class LocalSearchMain {

	public static void main(String[] args) throws TimeoutException {
		
		if (args.length < 1) {
			System.out.println("USAGE java -jar ff.jar <formula.cnf> [sparrow | ff | walksat]");
			return;
		}
		
		Stopwatch watch = new Stopwatch();
		CnfSatFormula f = CnfSatFormula.parseFromFile(args[0]);
		
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

		
		if (!f.validateSolution(solver.getModel())) {
			System.err.println("INVALID SOLUTION");
		}
		
		if (sat) {
			System.out.println("s SATISFIABLE");
		} else {
			System.out.println("s UNKNOWN");
		}

	}

}
