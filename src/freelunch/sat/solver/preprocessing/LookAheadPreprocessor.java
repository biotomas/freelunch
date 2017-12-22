package freelunch.sat.solver.preprocessing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.sat.bce.utilities.UnitPropagationSimplifier;
import freelunch.sat.bce.utilities.Utils;
import freelunch.sat.satLifter.sat.Propagator;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class LookAheadPreprocessor {
	
	private Propagator p;
	private BasicFormula f;
	private int learned;
	private boolean unsat;
	private Set<String> seenCls;

	
	/**
	 * Do formula preprocessing, return false is UNSAT
	 * @param f
	 * @return false if unsat
	 */
	public boolean preprocess(BasicFormula f) {
		this.f = f;
		learned = 0;
		unsat = false;
		seenCls  = new HashSet<String>();
		
		if (null == UnitPropagationSimplifier.simplifyByUnitPropagation(f, true)) {
			return false;
		}
		
		for (int[] cl : f.clauses) {
			addNewClause(cl);
		}
		
		initializePropagator();
		
		while (true) {
			if (learnUnits() > 0) {
				continue;
			}
			if (unsat) break;
			
			if (learnBinaries() > 0) {
				continue;
			}
			break;
			/*
			if (unsat) break;
			if (learnTernaries() > 0) {
				continue;
			}
			if (unsat) break;
			break;
			/**/
		}
		
		if (unsat) {
			return false;
		} else {
			return true;
		}
	}
	
	private void initializePropagator() {
		p = new Propagator(f.variablesCount);
		for (int[] cl : f.clauses) {
			p.addClause(cl);
		}
	}
	
	private boolean addNewClause(int ... lits) {
		String s = Arrays.toString(lits);
		return seenCls.add(s);
	}
	
	private boolean seenClause(int ... lits) {
		String s = Arrays.toString(lits);
		return seenCls.contains(s);		
	}
	
	
	
	private void addClause(int ... lits) {
		if (!addNewClause(lits)) {
			return;
		}
		//System.out.println("learnead a clause " + Arrays.toString(lits));
		learned++;
		f.clauses.add(lits);
		
		// learned a unit clause
		if (lits.length == 1) {
			List<Integer> res = UnitPropagationSimplifier.simplifyByUnitPropagation(f, true);
			if (res == null) {
				unsat = true;
				return;
			}
			initializePropagator();
		} else {
			p.addClause(lits);
		}
	}

	private int learnUnits() {
		int start = learned;
		List<Integer> posRes = null, negRes = null;
		
		for (int i = 1; i <= f.variablesCount; i++) {
			
			if (!seenClause(-i)) {
				p.restartPropagator();
				posRes = p.propagate(i);
				if (posRes == null) {
					addClause(-i);
					if (unsat) return 0;
				}
			}
			
			if (!seenClause(i)) {
				p.restartPropagator();
				negRes = p.propagate(-i);
				if (negRes == null) {
					addClause(i);
					if (unsat) return 0;
				}
			}

			// intersection
			if (posRes != null && negRes != null) {			
				posRes.retainAll(negRes);
				for (int l : posRes) {
					addClause(l);
					if (unsat) return 0;
				}
			} 
		}
		return learned - start;
	}
	
	@SuppressWarnings("unchecked")
	private int learnBinaries() {
		int start = learned;
		List<Integer> lff = null, ltt = null, ltf = null, lft = null;
		for (int v1 = 1; v1 <= f.variablesCount; v1++) {
			for (int v2 = v1+1; v2 <= f.variablesCount; v2++) {
				
				if (!seenClause(v1,v2)) {
					p.restartPropagator();
					lff = p.propagate(-v1,-v2);
					if (lff == null) {
						addClause(v1,v2);
					}
				}
				
				if (!seenClause(-v1,-v2)) {
					p.restartPropagator();
					ltt = p.propagate(v1,v2);
					if (ltt == null) {
						addClause(-v1,-v2);
					}
				}
					
				if (!seenClause(-v1,v2)) {
					p.restartPropagator();
					ltf = p.propagate(v1,-v2);
					if (ltf == null) {
						addClause(-v1,v2);
					}
				}
					
				if (!seenClause(v1,-v2)) {
					p.restartPropagator();
					lft = p.propagate(-v1,v2);
					if (lft == null) {
						addClause(v1,-v2);
					}
				}
				
				if (Utils.allNull(ltt,lff,ltf,lft)) {
					unsat = true;
					return 0;
				} else {
					List<Integer> res = Utils.getNotNull(ltt,lff,ltf,lft);
					if (ltt != null) res.retainAll(ltt);
					if (lff != null) res.retainAll(lff);
					if (ltf != null) res.retainAll(ltf);
					if (lft != null) res.retainAll(lft);
					
					for (int l : res) {
						addClause(l);
						if (unsat) return 0;
					}
				}
			}
		}
		return learned - start;
	}
	
	@SuppressWarnings("unused")
	private int learnTernaries() {
		int start = learned;
		int vars = f.variablesCount;
		
		for (int v1 = 1; v1 <= vars; v1++) {
			for (int v2 = v1+1; v2 <= vars; v2++) {
				for (int v3 = v2+1; v3 <= vars; v3++) {
					
					testThreeLits(v1,   v2,  v3);
					testThreeLits(v1,   v2, -v3);
					testThreeLits(v1,  -v2,  v3);
					testThreeLits(v1,  -v2, -v3);
					testThreeLits(-v1,  v2,  v3);
					testThreeLits(-v1,  v2, -v3);
					testThreeLits(-v1, -v2,  v3);
					testThreeLits(-v1, -v2, -v3);
					
				}
			}
		}
		return learned - start;
	}
	
	private void testThreeLits(int l1, int l2, int l3) {
		if (seenClause(-l1,-l2,-l3)) {
			return;
		}
		
		List<Integer> asg = new ArrayList<Integer>();
		asg.add(l1);
		asg.add(l2);
		asg.add(l3);
		
		p.restartPropagator();
		if (p.propagate(asg)) {
			addClause(-l1,-l2,-l3);
		}
	}
	

}
