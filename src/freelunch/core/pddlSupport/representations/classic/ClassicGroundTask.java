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

/**
 *
 * @author FD
 */
public class ClassicGroundTask {
    public String mName = "none";
    public List<Atom> atoms = new LinkedList<>();
    public List<Atom> init = new LinkedList<>();
    public List<Atom> goal = new LinkedList<>();
    public List<Action> actions = new LinkedList<>();

    @Override
    public String toString() {
        return mName+"|atoms:"+atoms.size()+"|actions:"+actions.size();
    }
    
}
