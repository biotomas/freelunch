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

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author FD
 */
public class SVariable {

    public String mName;
    public List<SValue> domain = new LinkedList<>();

    @Override
    public String toString() {
        return mName;
    }

    public int GetMyIndex() {
        return Integer.parseInt(mName.replace("var", ""));
    }

    public int GetIndexOfValue(SValue vl) {
        int valIndex = -1;
        int ct = 0;
        for (SValue val : domain) {
            if (vl.equals(val)) {
                valIndex = ct;
            }
            ct++;
        }
        return valIndex;
    }
}
