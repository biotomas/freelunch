package freelunch.sat.bce.encoding;

import java.util.ArrayList;

import freelunch.sat.satLifter.sat.DimacsParser.BasicFormula;

public class DualRailEncoder {
	
	public static BasicFormula getDualRailEncoding(BasicFormula f) {
		BasicFormula result = new BasicFormula();
		result.variablesCount = f.variablesCount*2;
		result.clauses = new ArrayList<int[]>();
		
		// px = 2*x
		// nx = 2*x - 1
		
		for (int i = 1; i <= f.variablesCount; i++) {
			result.clauses.add(new int[]{-getPX(i), -getNX(i)});
		}
		
		for (int[] cl : f.clauses) {
			int[] ncl = new int[cl.length];
			for (int i = 0; i < cl.length; i++) {
				ncl[i] = getDR(cl[i]);
			}
			result.clauses.add(ncl);
		}
		return result;
	}
	
	public static int getDR(int lit) {
		return lit > 0 ? getPX(lit) : getNX(-lit);
	}
	
	public static int getPX(int x) {
		return 2*x;
	}
	
	public static int getNX(int x) {
		return 2*x - 1;
	}

}
