package freelunch.planning.model;

import java.util.ArrayList;
import java.util.List;

public class ConditionalEffect {
	
	private StateVariable var;
	private int newValue;
	private SasAction action;
	private int id;
	
	private List<Condition> effectConditions;
	
	public ConditionalEffect(StateVariable var, int newValue, SasAction action) {
		this.var = var;
		this.newValue = newValue;
		this.effectConditions = new ArrayList<Condition>();
		this.action = action;
	}
	
	public StateVariable getVar() {
		return var;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public SasAction getAction() {
		return action;
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
