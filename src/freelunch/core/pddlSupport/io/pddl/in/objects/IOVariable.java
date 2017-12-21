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

/**
 *
 * @author FD
 */
public class IOVariable {

    public String mName = null;
    public String mType = null;

    public IOVariable(String name, String type) {
        mName = name;
        mType = type;
    }

    public IOVariable() {
    }

    @Override
    public String toString() {
        String nm = (mName == null)?"null":mName;
        String tp = (mType == null)?"null":mType;
        return nm+"("+tp+")";
    }

    public IOVariable cc(){
        return new IOVariable(mName, mType);
    }
    
    
    
    
}
