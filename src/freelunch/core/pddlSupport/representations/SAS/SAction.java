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
public class SAction {
    public int mCost;
    public String mName;
    public List<SAssignment> preconditions = new LinkedList<>();
    public List<SAssignment> effects = new LinkedList<>();

    @Override
    public String toString() {
        return mName;
    }
}
