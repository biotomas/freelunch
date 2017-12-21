/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.core.satSolving;

import freelunch.core.satModelling.modelObjects.BasicSatFormula;

public class FormulaAnalyzer {
    
    public static class FormulaProperties {
        public int vars = 0;
        public int clauses = 0;
        public int unit = 0;
        public int binary = 0;
        public int ternary = 0;
        public int longer = 0;
        public int horn = 0;
        public long totalLiterals = 0;
        
        public String csv() {
            return String.format("vars;%d;cls;%d;unit;%.1f;bin;%.1f;horn;%.1f",
                    vars, clauses, (float)100*unit / clauses, (float)100*binary / clauses, (float)100*horn / clauses);
        }
        
        public void print() {
            System.out.println(String.format("Vars: %d Clauses %d ratio %.3f avg. clause len. %.2f", 
                    vars, clauses, (float)vars / clauses, (float)totalLiterals/clauses));
            System.out.println(String.format("Unit clauses: %d (%.2f%%)", unit, (float)100*unit / clauses));
            System.out.println(String.format("Binary clauses: %d (%.2f%%)", binary, (float)100*binary / clauses));
            System.out.println(String.format("Ternary clauses: %d (%.2f%%)", ternary, (float)100*ternary / clauses));
            System.out.println(String.format("Longer clauses: %d (%.2f%%)", longer, (float)100*longer / clauses));
            System.out.println(String.format("Horn clauses: %d (%.2f%%)", horn, (float)100*horn / clauses));
        }
    }
    
    public static FormulaProperties analyzeFormula(BasicSatFormula formula) {
        FormulaProperties fp = new FormulaProperties();
        fp.vars = formula.getVariables();
        fp.clauses = formula.getClauses().size();
        int hornPos = 0;
        for (int[] clause : formula.getClauses()) {
            int pos = 0;
            for (int lit : clause) {
                if (lit > 0) pos++;
            }
            if (pos <= 1) hornPos++;
            
            fp.totalLiterals += clause.length;
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
        fp.horn = hornPos;
        return fp;
    }
    
}
