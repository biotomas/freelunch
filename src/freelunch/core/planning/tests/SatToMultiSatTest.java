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
package freelunch.core.planning.tests;

import junit.framework.TestCase;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.sase.sasToMultiSat.MultiValuedCNF;
import freelunch.core.planning.sase.sasToMultiSat.SasToMVSat;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;

public class SatToMultiSatTest extends TestCase {

    
    public void testTrivialExample() {
        
        SasProblem p = createProblem2().getSasProblem();
        SasToMVSat translator = new SasToMVSat();
        MultiValuedCNF cnf = translator.translate(p, 4);
        cnf.printNoGoodFormat(System.out);
        
        System.out.println("========================");

        p = createProblem().getSasProblem();
        cnf = translator.translate(p, 4);
        cnf.printNoGoodFormat(System.out);
    }
    
    private SasProblemBuilder createProblem2() {
        SasProblemBuilder prob = new SasProblemBuilder();
        StateVariable var1 = prob.newVariable("x1", 5);
        prob.addInitialStateCondition(new Condition(var1, 0));
        prob.addGoalCondition(new Condition(var1, 4));
        
        SasAction a = prob.newAction(new StringActionInfo("a1"));
        a.getPreconditions().add(new Condition(var1, 0));
        a.getEffects().add(new Condition(var1, 1));
        
        a = prob.newAction(new StringActionInfo("a2"));
        a.getPreconditions().add(new Condition(var1, 1));
        a.getEffects().add(new Condition(var1, 2));
        
        a = prob.newAction(new StringActionInfo("a3"));
        a.getPreconditions().add(new Condition(var1, 2));
        a.getEffects().add(new Condition(var1, 3));
        
        a = prob.newAction(new StringActionInfo("a4"));
        a.getPreconditions().add(new Condition(var1, 3));
        a.getEffects().add(new Condition(var1, 4));
        
        return prob;
    }
    
    private SasProblemBuilder createProblem() {
        SasProblemBuilder prob = new SasProblemBuilder();
        
        StateVariable var1 = prob.newVariable("x1", 3);
        StateVariable var2 = prob.newVariable("x2", 3);
        StateVariable var3 = prob.newVariable("x3", 3);
        
        prob.addInitialStateCondition(new Condition(var1, 0));
        prob.addInitialStateCondition(new Condition(var2, 0));
        prob.addInitialStateCondition(new Condition(var3, 0));
        prob.addGoalCondition(new Condition(var3, 2));
        
        
        SasAction a = prob.newAction(new StringActionInfo("a1"));
        a.getPreconditions().add(new Condition(var1, 0));
        a.getPreconditions().add(new Condition(var2, 0));
        a.getEffects().add(new Condition(var3, 1));
        
        a = prob.newAction(new StringActionInfo("a2"));
        a.getPreconditions().add(new Condition(var1, 0));
        a.getPreconditions().add(new Condition(var3, 1));
        a.getEffects().add(new Condition(var1, 1));
        a.getEffects().add(new Condition(var2, 1));
        
        a = prob.newAction(new StringActionInfo("a3"));
        a.getPreconditions().add(new Condition(var1, 1));
        a.getPreconditions().add(new Condition(var2, 1));
        a.getEffects().add(new Condition(var3, 2));
        a.getEffects().add(new Condition(var2, 1));
               
        return prob;
    }
    
}
