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
package freelunch.core.planning.sase.preprocessing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import freelunch.core.planning.model.ActionInfo;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;

public class DoubleActionCodec {
    
    public static class DoubleActionInfo implements ActionInfo {
        
        public SasAction a1, a2;
        
        public DoubleActionInfo(SasAction a1, SasAction a2) {
            this.a1 = a1;
            this.a2 = a2;
        }

        @Override
        public String getName() {
            return a1.getActionInfo().getName() + "+" + a2.getActionInfo().getName();
        }
    }
    
    private SasAction join(SasAction a1, SasAction a2) {
        SasAction result = new SasAction(new DoubleActionInfo(a1, a2));
        Set<Condition> preconditions = new HashSet<Condition>();
        Set<Condition> effects = new HashSet<Condition>();
        
        preconditions.addAll(a1.getPreconditions());
        preconditions.addAll(a2.getPreconditions());
        preconditions.removeAll(a1.getEffects());
        
        effects.addAll(a2.getEffects());
        effects.addAll(a1.getEffects());
        // remove non prevailing preconditions of a2
        for (Condition c : a2.getPreconditions()) {
            if (false == isPrevailCondition(c, a2)) {
                effects.remove(c);
            }
        }

        result.setPreconditions(new ArrayList<Condition>(preconditions));
        result.setEffects(new ArrayList<Condition>(effects));
        return result;
    }
    
    private boolean decideJoin(SasAction a1, SasAction a2) {
        for (Condition c : a1.getEffects()) {
            if (a2.getPreconditions().contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean actionsCompatible(SasAction a1, SasAction a2) {
        // test if an effect of a1 ruins a precondition of a2
        for (Condition e : a1.getEffects()) {
            if (conditionConflict(e, a2.getPreconditions())) {
                return false;
            }
        }
        // test if a prevail condition of a1 ruins a precondition of a2
        for (Condition p : a1.getPreconditions()) {
            if (isPrevailCondition(p, a1) && conditionConflict(p, a2.getPreconditions()) ) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Test if a given condition is in conflict with any of conditions
     * in the given list.
     * @param condition
     * @param conditionList
     * @return true if conflict occurs
     */
    private boolean conditionConflict(Condition condition, Collection<Condition> conditionList) {
        int varId = condition.getVariable().getId();
        for (Condition p : conditionList) {
            if (p.getVariable().getId() == varId && p.getValue() != condition.getValue()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Test if the given condition is prevailing condition of the given action.
     * We assume that that given condition is a preconditions of the action.
     * @param condition precondition of a
     * @param action action
     * @return true if prevailing condition
     */
    private boolean isPrevailCondition(Condition condition, SasAction action) {
        int varId = condition.getVariable().getId();
        for (Condition e : action.getEffects()) {
            if (e.getVariable().getId() == varId) {
                return false;
            }
        }
        return true;
    }
    
    public List<SasAction> makeActionPairs(List<SasAction> actions) {
        List<SasAction> result = new ArrayList<SasAction>();
        for (int i = 0; i < actions.size(); i++) {
            for (int j = 0; j < actions.size(); j++) {
                if (i == j) {
                    continue;
                }
                SasAction a1 = actions.get(i);
                SasAction a2 = actions.get(j);
                
                if (actionsCompatible(a1, a2) && decideJoin(a1, a2)) {
                    result.add(join(a1, a2));
                }
            }
        }
        return result;
    }

}
