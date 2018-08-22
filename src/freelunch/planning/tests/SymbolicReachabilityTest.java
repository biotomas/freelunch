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
package freelunch.planning.tests;

import java.io.IOException;

import freelunch.planning.cmdline.Translator.TranslationMethod;
import freelunch.planning.model.Condition;
import freelunch.planning.model.SasAction;
import freelunch.planning.model.SasProblem;
import freelunch.planning.model.StateVariable;
import freelunch.planning.model.StringActionInfo;
import freelunch.planning.planners.satplan.SasProblemBuilder;
import freelunch.planning.planners.satplan.symbolicReachability.SymbolicReachabilityProblemGenerator;
import freelunch.sat.reachability.SymbolicReachabilityProblem;
import junit.framework.TestCase;

public class SymbolicReachabilityTest extends TestCase {
	
	public void testSrtParser() {
		String filename = "testfiles/test.srt";
		try {
			SymbolicReachabilityProblem srp = new SymbolicReachabilityProblem(filename);
			System.out.println(srp.initialConditions.variablesCount);
			System.out.println(srp.universalConditions.variablesCount);
			System.out.println(srp.transitionConditions.variablesCount);
			System.out.println(srp.goalConditions.variablesCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testTrivialExample() {
		
		SasProblem p = createProblem().getSasProblem();
		SymbolicReachabilityProblemGenerator gen = new SymbolicReachabilityProblemGenerator(p, TranslationMethod.sase);
		SymbolicReachabilityProblem prob = gen.getSRProblem();
		prob.print(System.out);
	}
	
	public static SasProblemBuilder createProblem() {
		SasProblemBuilder prob = new SasProblemBuilder();
		
		StateVariable var = prob.newVariable("x", 2);
		
		prob.addInitialStateCondition(new Condition(var, 0));
		prob.addGoalCondition(new Condition(var, 1));
		
		SasAction a = prob.newAction(new StringActionInfo("test"));
		a.getPreconditions().add(new Condition(var, 0));
		a.getEffects().add(new Condition(var, 1));
		
		return prob;
	}

}
