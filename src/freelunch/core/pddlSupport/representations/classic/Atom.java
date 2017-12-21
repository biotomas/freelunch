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

package freelunch.core.pddlSupport.representations.classic;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;

/**
 *
 * @author FD
 */
public class Atom {
    public String mName;

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Atom)obj).mName.equals(mName);        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.mName);
        return hash;
    }
    
    public String GetPredicateName(){
        return mName.split("\\(")[0];
    }
    
    public List<IOVariable> GetObjectNames(){
        List<IOVariable> ret = new LinkedList<>();
        String[] ar = mName.split("\\(")[1].split("\\)")[0].split(",");
        for(int i = 0; i < ar.length; i++){
            IOVariable lit = new IOVariable();
            lit.mName = ar[i];
            ret.add(lit);
        }        
        return ret;
    }        
}
