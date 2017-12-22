package freelunch.sat.solver.refutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.utilities.ClauseIndex;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;
import freelunch.sat.solver.SatSolver;

public class ResolutionRefutation implements SatSolver {
	
	public static void extendAllVariables(BasicFormula f, int rounds) {
		for (int i = 0; i < rounds; i++) {
			int lastVar = f.variablesCount;
			for (int var1 = 1; var1 <= f.variablesCount; var1++) {
				for (int var2 = var1+1; var2 <= f.variablesCount; var2++) {
					lastVar++;
					f.clauses.add(new int[]{-lastVar, var1});
					f.clauses.add(new int[]{-lastVar, var2});
					f.clauses.add(new int[]{lastVar, -var1, -var2});
				}
			}
			f.variablesCount = lastVar;
		}
	}
	

	@Override
	public Boolean isSatisfiable(BasicFormula formula) {
		Set<String> clauseset = new HashSet<String>();
		ClauseIndex ci = new ClauseIndex(formula);
		for (int[] cl : formula.clauses) {
			Arrays.sort(cl);
			clauseset.add(Arrays.toString(cl));
		}
		while(true) {
			List<int[]> toAdd = new ArrayList<int[]>();
			for (int[] cl : formula.clauses) {
				for (int lit : cl) {
					for (int[] othercl : ci.getClausesWith(-lit)) {
						int[] resolvent = ClauseIndex.resolve(cl, othercl);
						if (resolvent != null) {
							if (resolvent.length == 0) {
								System.out.println("Empty Clause Added!");
								return false;
							}
							if (resolvent.length > 3) {
								continue;
							}
							Arrays.sort(resolvent);
							String s = Arrays.toString(resolvent);
							if (clauseset.contains(s) == false) {
								clauseset.add(s);
								toAdd.add(resolvent);
							}
						}
					}
				}
			}
			for (int[] cl : toAdd) {
				ci.addClause(cl);
				formula.clauses.add(cl);
			}
			System.out.println("Added " + toAdd.size() + " resolvents, total size is " + clauseset.size());
			if (toAdd.size() == 0) {
				break;
			}
		}
		return true;
	}

	@Override
	public int[] getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTimeout(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

}
