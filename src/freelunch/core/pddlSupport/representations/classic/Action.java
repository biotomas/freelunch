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

/**
 *
 * @author FD
 */
public class Action {
    public String mName;
    public List<Atom> preconditions = new LinkedList<>(); 
    public List<Atom> positiveEffects = new LinkedList<>(); 
    public List<Atom> negativeEffects = new LinkedList<>();
    public int mCost;

    @Override
    public String toString() {
        return mName;
    }
    @Override
    public boolean equals(Object obj) {
        return ((Action)obj).mName.equals(mName);        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.mName);
        return hash;
    }
    
     
}
