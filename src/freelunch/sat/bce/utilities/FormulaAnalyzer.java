package freelunch.sat.bce.utilities;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

import freelunch.sat.satLifter.sat.DimacsParser;
import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class FormulaAnalyzer {
    
    public static class FormulaProperties {
        public int vars = 0;
        public int clauses = 0;
        public int unit = 0;
        public int binary = 0;
        public int ternary = 0;
        public int longer = 0;
        public int appearingVariables = 0;
        public long totalLiterals = 0;
        
        public void print() {
            System.out.println(String.format("Vars: %d Clauses %d ratio %.3f avg. clause len. %.2f", 
                    vars, clauses, (float)vars / clauses, (float)totalLiterals/clauses));
            System.out.println(String.format("Present variables: %d (%.2f%%)", appearingVariables, (float)100*appearingVariables / vars));
            System.out.println(String.format("Unit clauses: %d (%.2f%%)", unit, (float)100*unit / clauses));
            System.out.println(String.format("Binary clauses: %d (%.2f%%)", binary, (float)100*binary / clauses));
            System.out.println(String.format("Ternary clauses: %d (%.2f%%)", ternary, (float)100*ternary / clauses));
            System.out.println(String.format("Longer clauses: %d (%.2f%%)", longer, (float)100*longer / clauses));
        }
        
        public String getCSV() {
        	return String.format(";var;%d;actvar;%d;cls;%d;unit;%d;bin;%d;tri;%d;long;%d", vars, appearingVariables, clauses, unit, binary, ternary, longer);
        }
    }
    
    public static FormulaProperties analyzeFormula(BasicFormula formula) {
        FormulaProperties fp = new FormulaProperties();
        fp.vars = formula.variablesCount;
        fp.clauses = formula.clauses.size();
        Set<Integer> variables = new HashSet<Integer>();
        for (int[] clause : formula.clauses) {
            fp.totalLiterals += clause.length;
            for (int lit : clause) {
            	int var = Math.abs(lit);
            	variables.add(var);
            }
            switch (clause.length) {
            case 1:
                fp.unit++;
                break;
            case 2:
                fp.binary++;
                break;
            case 3: 
                fp.ternary++;
                break;
            default:
                fp.longer++;
            }
        }
        fp.appearingVariables = variables.size();
        return fp;
    }
    
    public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("USAGE java -jar fanalyzer.jar <formula.cnf> [-c csv]");
			return;
		}
		BasicFormula f = DimacsParser.parseFromFile(args[0]);
		if (f == null) {
			System.err.println("File not found");
			return;
		}
		FormulaProperties fp = analyzeFormula(f);
		if (args.length > 1) {
			System.out.println(args[0] + fp.getCSV());
		} else {
			fp.print();
		}
	}
    
    public static BitSet commonVariables(BasicFormula l, BasicFormula r) {
    	BitSet lvars = new BitSet(l.variablesCount+1);
    	BitSet rvars = new BitSet(l.variablesCount+1);
    	lvars.clear();
    	rvars.clear();
		for (int[] cl : l.clauses) {
			for (int lit : cl) {
				lvars.set(Math.abs(lit));
			}
		}
		for (int[] cl : r.clauses) {
			for (int lit : cl) {
				rvars.set(Math.abs(lit));
			}
		}
    	lvars.and(rvars);
    	return lvars;
    }
    
    public static int commonBlitVariables(BasicFormula l, BasicFormula r) {
    	BitSet lvars = new BitSet(l.variablesCount+1);
    	BitSet rvars = new BitSet(l.variablesCount+1);
    	lvars.clear();
    	rvars.clear();
		for (int[] cl : l.clauses) {
			lvars.set(Math.abs(cl[0]));
		}
		for (int[] cl : r.clauses) {
			rvars.set(Math.abs(cl[0]));
		}
    	lvars.and(rvars);
    	return lvars.cardinality();
    }

	public static String compareFormulas(BasicFormula l, BasicFormula r) {
		Set<Integer> varsl = new HashSet<Integer>();
		Set<Integer> varsr = new HashSet<Integer>();
		Set<Integer> blitl = new HashSet<Integer>();
		Set<Integer> blitr = new HashSet<Integer>();
		for (int[] cl : l.clauses) {
			blitl.add(Math.abs(cl[0]));
			for (int lit : cl) varsl.add(Math.abs(lit));
		}
		for (int[] cl : r.clauses) {
			blitr.add(Math.abs(cl[0]));
			for (int lit : cl) varsr.add(Math.abs(lit));
		}
		Set<Integer> intersect = new HashSet<Integer>(varsl);
		intersect.retainAll(varsr);
		Set<Integer> bintersect = new HashSet<Integer>(blitl);
		bintersect.retainAll(blitr);
		return String.format("c vars L:%d R: %d (%d%%) common: %d blits L:%d R: %d (%d%%) common: %d", 
				varsl.size(), varsr.size(), 100*varsr.size()/l.variablesCount, intersect.size(),
				blitl.size(), blitr.size(), 100*blitr.size()/l.variablesCount, bintersect.size());
	}

	public static boolean solutionOk(BasicFormula f, int[] assignment) {
		clausesloop:
		for (int[] c : f.clauses) {
			for (int l : c) {
				int var = Math.abs(l);
				if (assignment[var] == l) {
					continue clausesloop;
				}
			}
			System.out.println("Unsat clause: " + Arrays.toString(c));
			return false;
		}
		return true;
	}
    
}