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
public class IOPredicate {
    public String mName = null;
    public LinkedList<IOVariable> mVars = new LinkedList<>(); 
    public boolean isStatic;

    @Override
    public String toString() {
        String ret = mName+"(";
        for(IOVariable v:mVars){
            ret += v+", ";
        }
        if(!mVars.isEmpty()){
            ret = ret.substring(0, ret.length()-2);
        }
        ret += ")";
        return ret;
    }

    public boolean IsDerivedFromMe(IOLiteral f) {
        return f.mName.equals(mName);
    }
    
}
