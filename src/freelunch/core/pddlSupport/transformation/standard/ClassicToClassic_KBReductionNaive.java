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
package freelunch.core.pddlSupport.transformation.standard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.representations.classic.Action;
import freelunch.core.pddlSupport.representations.classic.Atom;
import freelunch.core.pddlSupport.representations.classic.ClassicGroundTask;

/**
 *
 * @author FD
 */
public class ClassicToClassic_KBReductionNaive {

    private static boolean isApplicable(HashSet<Atom> kb, Action a) {
        for (Atom at : a.preconditions) {
            if (!kb.contains(at)) {
                return false;
            }
        }
        return true;
    }
    
    private static void apply(HashSet<Atom> kb, Action a){
        for(Atom at:a.positiveEffects){
            kb.add(at);
        }
    }

    private static boolean triviallySatisfiable(HashSet<Atom> kb, List<Atom> goal){
        for(Atom at:goal){
            if(!kb.contains(at)){
                return false;
            }
        }
        return true;
    }
    
    public static ClassicGroundTask Transform(ClassicGroundTask in) {
        List<Action> actionsToTry = new LinkedList<>(in.actions);
        List<Action> appliedActions = new LinkedList<>();
        HashSet<Atom> kb = new HashSet<>(in.init);
        int oldSize = -1;
        while (oldSize != kb.size()) {
            oldSize = kb.size();
            Iterator<Action> it = actionsToTry.iterator();
            while (it.hasNext()) {
                Action ac = it.next();
                if (isApplicable(kb, ac)) {
                    apply(kb, ac);
                    it.remove();
                    appliedActions.add(ac);
                }
            }
        }
        if(!triviallySatisfiable(kb, in.goal)){
            throw new UnsupportedOperationException("The planning task failed the test of relaxed reachability of the goal.");
        }
        ClassicGroundTask ret = new ClassicGroundTask();
        ret.init = in.init;
        ret.goal = in.goal;
        ret.actions = new LinkedList<>(appliedActions);
        ret.atoms = new LinkedList<>(kb);
        ret.mName = in.mName + "//ClassicToClassic_KBReductionNaive";
        return ret;
    }
}
