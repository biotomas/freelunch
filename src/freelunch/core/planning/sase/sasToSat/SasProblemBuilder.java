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
package freelunch.core.planning.sase.sasToSat;

import java.util.ArrayList;
import java.util.List;

import freelunch.core.planning.model.ActionInfo;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;


/**
 * Class for easier SASProblem construction
 * 
 * @author Tomas Balyo Aug 30, 2012
 */
public class SasProblemBuilder {

    private final List<StateVariable> variables;
    private final List<SasAction> operators;
    private final List<Condition> initialState;
    private final List<Condition> goalState;
    private int variableId;

    public SasProblemBuilder() {
        variables = new ArrayList<StateVariable>();
        operators = new ArrayList<SasAction>();
        initialState = new ArrayList<Condition>();
        goalState = new ArrayList<Condition>();
        variableId = 0;
    }

    /**
     * Create and register a new action (operator) with the given name.
     * 
     * @param actionInfo
     * @return
     */
    public SasAction newAction(ActionInfo actionInfo) {
        SasAction op = new SasAction(actionInfo);
        op.setPreconditions(new ArrayList<Condition>());
        op.setEffects(new ArrayList<Condition>());
        operators.add(op);
        return op;
    }

    /**
     * Create and register a new state variable with given domain size. The
     * domain values are 0,1,...,domainSize - 1
     * 
     * @param domainSize
     * @return
     */
    public StateVariable newVariable(String name, int domainSize) {
        StateVariable var = new StateVariable(name, variableId++, domainSize);
        variables.add(var);
        return var;
    }

    /**
     * Adds a new initial state condition describing the initial state of the
     * planning problem.
     * 
     * @param condition
     */
    public void addInitialStateCondition(Condition condition) {
        initialState.add(condition);
    }

    /**
     * Adds a new goal condition describing the desired end state.
     * 
     * @param condition
     */
    public void addGoalCondition(Condition condition) {
        goalState.add(condition);
    }

    /**
     * Get the defined SAS problem
     * 
     * @return
     */
    public SasProblem getSasProblem() {
        SasProblem prob = new SasProblem();
        prob.setVariables(variables);
        prob.setOperators(operators);
        prob.setInitialState(initialState);
        prob.setGoal(goalState);
        return prob;
    }

}
