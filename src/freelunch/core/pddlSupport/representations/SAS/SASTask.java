/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2012 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */

package freelunch.core.pddlSupport.representations.SAS;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author FD
 */
public class SASTask {
    public String mName = "none";
    public List<SVariable> variables = new LinkedList<>();
    public List<SAssignment> init = new LinkedList<>();
    public List<SAssignment> goal = new LinkedList<>();
    public List<SAction> actions = new LinkedList<>();

    @Override
    public String toString() {
        return mName+"|variables:"+variables.size()+"|actions:"+actions.size();
    }

    public int GetDummyValueForVariable(int i) {
        for(SVariable var:variables){
            if(var.GetMyIndex() == i){
                return var.domain.size()-1;
            }
        }
        throw new UnsupportedOperationException();
    }
}
