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
package freelunch.core.planning.sase.api.lifted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiftedAction {
	
	private String nameFormat;
	private Map<String, NamedParameter> argumentsMap;
	private List<PlanningObjectRelationClass> requiredRelations;	
	private List<LiftedCondition> preconditions;
	private List<LiftedCondition> effects;
	private List<LiftedCondition> prevailConditions;
	
	public LiftedAction(String nameFormat, NamedParameter ... arguments) {
		this.nameFormat = nameFormat;
		argumentsMap = new HashMap<String, NamedParameter>(arguments.length);
		for (NamedParameter param : arguments) {
			argumentsMap.put(param.getName(), param);
		}
		preconditions = new ArrayList<LiftedCondition>();
		effects = new ArrayList<LiftedCondition>();
		prevailConditions = new ArrayList<LiftedCondition>();
	}
	
	public void addRequiredRelation(PlanningObjectRelationClass requiredRelation, ParameterBinding ... parameters) throws PlanningProblemModellingException {
		if (requiredRelations == null) {
			requiredRelations = new ArrayList<PlanningObjectRelationClass>();
		}
		requiredRelations.add(requiredRelation);
		
		// check the relation typing validity
		for (ParameterBinding bind : parameters) {
			if (!requiredRelation.getArgumentClasses().containsKey(bind.getParameter())) {
				throw new PlanningProblemModellingException(String.format("Relation parameter name `%s` is undefined", bind.getParameter()));
			}
			if (!argumentsMap.containsKey(bind.getValue())) {
				throw new PlanningProblemModellingException(String.format("Action parameter name `%s` is undefined", bind.getValue()));
			}
			
			PlanningObjectClass requiredType = requiredRelation.getArgumentClasses().get(bind.getParameter()).getType();
			PlanningObjectClass providedType = argumentsMap.get(bind.getValue()).getType();
			
			if (!requiredType.isSuperClassOf(providedType)) {
				throw new PlanningProblemModellingException(String.format("Relation and action parameter type mismatch." +
						"The type `%s` of the action parameter `%s` is not a superclass of the relation parameter `%s` with " +
						"type `%s`.", requiredType.getName(), bind.getParameter(), providedType.getName(), bind.getValue()));
			}
		}
	}
	
	public void addPrecondition(LiftedStateVariable variable, String variableParam, String valueParam) throws PlanningProblemModellingException {
		preconditions.add(makeCondition(variable, variableParam, valueParam));
	}
	
	public void addEffect(LiftedStateVariable variable, String variableParam, String valueParam) throws PlanningProblemModellingException {
		effects.add(makeCondition(variable, variableParam, valueParam));
	}
	
	public void addPrevailCondition(LiftedStateVariable variable, String variableParam, String valueParam) throws PlanningProblemModellingException {
		prevailConditions.add(makeCondition(variable, variableParam, valueParam));
	}
	
	private LiftedCondition makeCondition(LiftedStateVariable variable, String varParam, String valParam) throws PlanningProblemModellingException {
		LiftedCondition condition = new LiftedCondition(variable, argumentsMap.get(varParam), argumentsMap.get(valParam));
		checkConditionValidity(condition);
		return condition;
	}
	
	private void checkConditionValidity(LiftedCondition condition) throws PlanningProblemModellingException {
		if (!condition.getVariable().getParameterClass().isSuperClassOf(condition.getVariableIndex().getType())) {
			throw new PlanningProblemModellingException(String.format("The parameter type `%s` of the condition state " +
					"variable `%s` is not a subtype of the action parameter type `%s`", condition.getVariable().getParameterClass().getName(),
					condition.getVariable().getName(), condition.getVariableIndex().getType().getName()));
		}
		if (!condition.getVariable().getValueClass().isSuperClassOf(condition.getValueIndex().getType())) {
			throw new PlanningProblemModellingException(String.format("The value type `%s` of the condition state " +
					"variable `%s` is not a subtype of the action parameter type `%s`", condition.getVariable().getParameterClass().getName(),
					condition.getVariable().getName(), condition.getVariableIndex().getType().getName()));
		}
	}
	
	@Override
	public String toString() {
		return nameFormat + "(" + argumentsMap.toString() + ")";
	}

}
