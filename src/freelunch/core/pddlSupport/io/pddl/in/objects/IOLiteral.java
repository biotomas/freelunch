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
public class IOLiteral {
    public String mName = null;
    public boolean positive = true;
    public LinkedList<IOVariable> vars = new LinkedList<>();
    //public boolean numeric = false;
    //public String funcValue = null;


    @Override
    public String toString() {
        
        
        String ret = mName+"(";
        for(IOVariable v:vars){
            ret += v+", ";
        }
        if(!vars.isEmpty()){
            ret = ret.substring(0, ret.length()-2);
        }
        ret += ")";
        return ret;
    }    
    
    public String GetPredicateName(){
        return mName.split("\\(")[0];
    }
}
