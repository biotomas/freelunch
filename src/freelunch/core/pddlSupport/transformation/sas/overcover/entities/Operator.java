/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2011 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */

package freelunch.core.pddlSupport.transformation.sas.overcover.entities;


import java.util.LinkedList;

import freelunch.core.pddlSupport.io.pddl.in.objects.IOAction;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOLiteral;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOPredicate;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;

/**
 *
 * @author Filip Dvořák
 */
public class Operator {
    public String mName;
    public LinkedList<LinkedList<IOVariable>> parameterInstances = new LinkedList<>();
    public LinkedList<IOVariable> parameters = new LinkedList<>();
    public LinkedList<IOLiteral> preconditions = new LinkedList<>();
    public LinkedList<IOLiteral> effectsNegative = new LinkedList<>();
    public LinkedList<IOLiteral> effectsPositive = new LinkedList<>();



    /**
     *
     * @param pars
     * @param vars
     * @param mA
     */
    public Operator(LinkedList<LinkedList<IOVariable>> pars, LinkedList<IOVariable> vars, IOAction mA){
        parameterInstances = pars;
        parameters = vars;
        mName = mA.mName;

        for(IOLiteral a : mA.conditions){
            if(!a.positive){
                throw new UnsupportedOperationException();
            }else{
                preconditions.add(a);
            }
        }
        for(IOLiteral a:mA.effects){
            if(a.positive){
                effectsPositive.add(a);
            }else{
                effectsNegative.add(a);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mName);
        return sb.toString();
    }

    public boolean ContainsInCondition(IOPredicate w) {
        for(IOLiteral f:preconditions){
            if(w.mName.equals(f.mName)){
                return true;
            }
        }
        return false;
    }


}
