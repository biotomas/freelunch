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

import java.util.Objects;

/**
 *
 * @author FD
 */
public class SValue {
    public String mName;

    @Override
    public boolean equals(Object obj) {
        return mName.equals(((SValue)obj).mName);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.mName);
        return hash;
    }

    @Override
    public String toString() {
        return mName;
    }
    
    
    
}
