package freelunch.planning.model;

import java.util.ArrayList;
import java.util.List;

public class ConditionalEffect {
	
	private StateVariable var;
	private int newValue;
	
	private List<Condition> effectConditions;
	
	public ConditionalEffect(StateVariable var, int newValue) {
		this.var = var;
		this.newValue = newValue;
		this.effectConditions = new ArrayList<Condition>();
	}
	
	
	public StateVariable getVar() {
		return var;
	}
	
	public void addEffectCondition(Condition condition) {
		this.effectConditions.add(condition);
	}
	
	public void setVar(StateVariable var) {
		this.var = var;
	}
	
	public int getNewValue() {
		return newValue;
	}
	
	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}

	public List<Condition> getEffectConditions() {
		return effectConditions;
	}
	
	@Override
	public String toString() {
		return String.format("var%d = %d under %s", var.getId(), newValue, effectConditions.toString());
	}

}
