/*
 * Author:  Filip Dvořák <filip.dvorak@runbox.com>
 *
 * Copyright (c) 2011 Filip Dvořák <filip.dvorak@runbox.com>, all rights reserved
 *
 * Publishing, providing further or using this program is prohibited
 * without previous written permission of the author. Publishing or providing
 * further the contents of this file is prohibited without previous written
 * permission of the author.
 */
package freelunch.core.pddlSupport.transformation.sas.overcover.entities;

import java.util.ArrayList;

/**
 *
 * @author Filip Dvořák
 */
public class Type {

    /**
     *
     */
    public String mName;
    /**
     *
     */
    public Type parentType;
    /**
     *
     */
    public ArrayList<Type> childrenTypes;

    /**
     *
     * @param name
     * @param parent
     */
    public Type(String name, Type parent) {
        mName = name;
        parentType = parent;
        childrenTypes = new ArrayList<>();
        if (parent != null) {
            parent.childrenTypes.add(this);
        }
    }

    /**
     *
     * @param type
     * @return
     */
    public boolean IsDerivedFromMe(String type) {
        if (mName.equals(type)) {
            return true;
        }
        for (int i = 0; i < childrenTypes.size(); i++) {
            if (childrenTypes.get(i).IsDerivedFromMe(type)) {
                return true;
            }
        }
        return false;
    }
}
