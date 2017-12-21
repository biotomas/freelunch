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
package freelunch.core.pddlSupport.io.pddl.in.objects;

import java.util.LinkedList;

/**
 *
 * @author FD
 */
public class IOAction {
    public int cost;
    public String mName = null;
    public LinkedList<IOVariable> parameters = new LinkedList<>();
    public LinkedList<IOLiteral> conditions = new LinkedList<>();
    public LinkedList<IOLiteral> effects = new LinkedList<>();

    @Override
    public String toString() {
        String ret = mName + "(";
        for(IOVariable v:parameters){
            ret += v+", ";
        }
        if(!parameters.isEmpty()){
            ret = ret.substring(0, ret.length()-2);
        }
        ret += ")\n";
        ret += "con: ";
        for(IOLiteral v:conditions){
            ret += v+", ";
        }
        if(!conditions.isEmpty()){
            ret = ret.substring(0, ret.length()-2);
        }
        ret += "\n";
        ret += "eff: ";
        for(IOLiteral v:effects){
            ret += v+", ";
        }
        if(!effects.isEmpty()){
            ret = ret.substring(0, ret.length()-2);
        }
        ret += "\n";
        return ret;
    }
    
    
}
