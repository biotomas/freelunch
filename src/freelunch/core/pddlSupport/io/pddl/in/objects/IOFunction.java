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
public class IOFunction {
    public String mName = null;
    public LinkedList<IOVariable> mVars = new LinkedList<>();
    public IOVariable mOutputType = null; //if there is multiple types i consider just one type that is contains them all
    public String mStartingValue = null; //used only in situations, when we inicialize by this variable
    public boolean isNumeric = false;

    @Override
    public String toString() {
        String ret = mName+": ";
        for(IOVariable v:mVars){
            ret += v+"; ";
        }
        if(!mVars.isEmpty()){
            ret = ret.substring(0, ret.length()-2);
        }
        ret += "-> ";
        ret += mOutputType;
        ret += " [init: "+mStartingValue+"]";
        return ret;
    }
    
}
