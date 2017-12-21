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
public class IOType {
    public String mName = null;
    public String mType = null;
    
    @Override
    public String toString() {
        String nm = (mName == null)?"null":mName;
        String tp = (mType == null)?"null":mType;
        return nm+"("+tp+")";
    }
}
