package freelunch.sat.bce.encoding.aig;

import java.security.InvalidParameterException;
import java.util.BitSet;

import freelunch.sat.bce.encoding.aig.AigerCircuit.AndGate;
import freelunch.sat.bce.utilities.Logger;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class ImprovedAigEncoder {
	
	protected int[] currentName;
	protected int lastNewVar;
	protected BitSet isInput;
	
	public AigerCircuit encode(BasicFormula formula) {
		Logger.print(1, "c starting aiger circuit construction");
		AigerCircuit ac = new AigerCircuit();
		currentName = new int[formula.variablesCount+1];
		isInput = new BitSet(formula.variablesCount+1);
		BitSet seenAsBlit = new BitSet(formula.variablesCount+1);
		isInput.clear();
		seenAsBlit.clear();
		for (int ind = formula.clauses.size() - 1; ind > 0; ind--) {
			int[] cl = formula.clauses.get(ind);
			int blitVar = Math.abs(cl[0]);
			seenAsBlit.set(blitVar);
			for (int i = 1; i < cl.length; i++) {
				int var = Math.abs(cl[i]);
				if (!seenAsBlit.get(var)) {
					isInput.set(var);
				}
			}
		}		
		
		lastNewVar = formula.variablesCount;
		for (int var = 1; var <= formula.variablesCount; var++) {
			currentName[var] = var;
			if (isInput.get(var)) {
				ac.getInputs().add(var);
			}
		}

		int[] first = formula.clauses.get(0);
		if (first.length != 1) {
			throw new InvalidParameterException("first clause must be unit");
		}
		for (int ind = formula.clauses.size() - 1; ind > 0; ind--) {
			int[] cl = formula.clauses.get(ind);
			addGates(cl, ac);
		}
		int outLit = first[0];
		ac.getOutputs().add(getCurrentLit(outLit));
		ac.setTotalVariables(lastNewVar);
		return ac;
	}
	
	protected int getCurrentLit(int lit) {
		return lit > 0 ? currentName[lit] : -currentName[-lit];
	}
	protected void addGates(int[] cl, AigerCircuit ac) {
		int blit = cl[0];

		if (cl.length == 1) {
			throw new InvalidParameterException("unit clause in make gate");
		}
		int origName = Math.abs(blit);
		int prevName = currentName[origName];
		
		// definition of a non input
		if (origName == prevName && !isInput.get(origName)) {
			
			//TODO test and debug
			if (cl.length == 2) {
				currentName[origName] = blit > 0 ? -getCurrentLit(cl[1]) : getCurrentLit(cl[1]);
			} else {
				int last = cl.length - 1;
				ac.getGates().add(new AndGate(++lastNewVar, -getCurrentLit(cl[last]), -getCurrentLit(cl[last-1])));
				for (int i = cl.length - 3; i > 0; i--) {
					ac.getGates().add(new AndGate(lastNewVar+1, -getCurrentLit(cl[i]), lastNewVar));
					lastNewVar++;
				}
				if (blit > 0) {
					currentName[origName] = blit > 0 ? -lastNewVar : lastNewVar;
				} else {
					currentName[origName] = blit > 0 ? lastNewVar : -lastNewVar;
				}
			}
			return;
		}
		
		int newName = ++lastNewVar;
		currentName[origName] = newName;
		if (blit > 0) {
			currentName[origName] *= -1;
		}
		if (cl.length == 2) {
			if (blit > 0) {
				ac.getGates().add(new AndGate(newName, -prevName, getCurrentLit(cl[1])));
			} else {
				ac.getGates().add(new AndGate(newName, prevName, getCurrentLit(cl[1])));
			}
		} else {
			int last = cl.length - 1;
			ac.getGates().add(new AndGate(++lastNewVar, -getCurrentLit(cl[last]), -getCurrentLit(cl[last-1])));
			for (int i = cl.length - 3; i > 0; i--) {
				ac.getGates().add(new AndGate(lastNewVar+1, -getCurrentLit(cl[i]), lastNewVar));
				lastNewVar++;
			}
			if (blit > 0) {
				ac.getGates().add(new AndGate(newName, -prevName, -lastNewVar));
			} else {
				ac.getGates().add(new AndGate(newName, prevName, -lastNewVar));
			}
		}
	}

}
