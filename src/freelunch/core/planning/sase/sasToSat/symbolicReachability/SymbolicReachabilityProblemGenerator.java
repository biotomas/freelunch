/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.core.planning.sase.sasToSat.symbolicReachability;

import java.util.List;

import freelunch.core.planning.cmdline.Translator;
import freelunch.core.planning.cmdline.Translator.TranslationMethod;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.satSolving.BasicSatFormulaGenerator;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilityProblem;


public class SymbolicReachabilityProblemGenerator {
    
    private SasToSatTranslator translator = null;
    private int variables;

    public SymbolicReachabilityProblemGenerator(SasProblem problem, TranslationMethod translation) {
        translator = Translator.makeTranslator(problem, translation, 5);
        translator.setMaxTimespan(0);
        variables = translator.setMaxTimespan(1);
    }
    
    public SasParallelPlan decodePlan(List<int[]> model) {
        return translator.decodePlan(model);
    }
    
    public int getVariables() {
    	return variables;
    }
	
	public SymbolicReachabilityProblem getSRProblem() {
			
        BasicSatFormulaGenerator solver = new BasicSatFormulaGenerator();
		SymbolicReachabilityProblem srProblem = new SymbolicReachabilityProblem();
		
		try {
			// action variables
			srProblem.actionVariables = translator.getActionVariables();
			// initial conditions
			solver.setVariablesCount(variables / 2);
			translator.addInitialStateConstraints(solver);
			srProblem.initialConditions = solver.getFormula();
			solver.clear();
			// goal conditions
			solver.setVariablesCount(variables / 2);
			translator.setGoalStateConstraints(solver, 0);
			srProblem.goalConditions = solver.getFormula();
			solver.clear();
			// universal conditions
			solver.setVariablesCount(variables / 2);
			translator.addUniversalStateConstraints(solver, 0);
			srProblem.universalConditions = solver.getFormula();
			solver.clear();
			// transition conditions
			solver.setVariablesCount(variables);
			translator.addTransitionConstraints(solver, 0);
			srProblem.transitionConditions = solver.getFormula();
		} catch (SatContradictionException e) {
			e.printStackTrace();
		}
		
		return srProblem;
	}

}
