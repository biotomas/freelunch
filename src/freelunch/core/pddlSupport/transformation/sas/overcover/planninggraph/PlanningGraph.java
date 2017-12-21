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
package freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.transformation.sas.overcover.entities.ActionInstance;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;

/**
 *
 * @author FD
 */
public class PlanningGraph {

    public List<ActionLayer> actionLayers = new LinkedList<>();
    public List<AtomLayer> atomLayers = new LinkedList<>();
    public List<MutexActions> actionMutexLayer = new LinkedList<>();
    public List<MutexAtoms> atomMutexLayer = new LinkedList<>();
    //useful variables
    HashMap<StandardAtom, HashSet<ActionInstance>> ef2act = new HashMap<>(); //cumulative map of pozitive effect -> action instance
    public List<StandardAtom> atoms = new LinkedList<>();
    public List<StandardAtom> init = new LinkedList<>();
    public List<StandardAtom> goal = new LinkedList<>();
    public List<ActionInstance> actions = new LinkedList<>();
    public LinkedHashSet<StandardAtom> cumulativeAtomSet = new LinkedHashSet<>();
    public LinkedHashSet<ActionInstance> cumulativeActionSet = new LinkedHashSet<>();

    private static ActionLayer allApplicableActions(MutexAtoms atomMutexes, LinkedHashSet<StandardAtom> currentAtoms, List<ActionInstance> actions) {
        ActionLayer ret = new ActionLayer();
        for (ActionInstance a : actions) {
            boolean debug = false;
            /*
             for (StandardAtom at : a.effectsPositive) {
             if (at.name.equals("passenger-at(p0, n3)")) {
             debug = true;
             System.out.println();
             System.out.print("Action: " + a);
             }
             }*/
            /*
            if (false && a.name.equals("leave(p0, fast0, n3, n1, n0)")) {
                debug = true;
                System.out.println();
                System.out.print("Action: " + a);
            }
            */
            if (!a.IsApplicableClean(currentAtoms)) {
                if (debug) {
                    System.out.print(">continue");
                }
                continue;
            } else {
                if (debug) {
                    System.out.print(">fine");
                }
            }
            boolean isOk = true;
            for (StandardAtom at1 : a.precondition) {
                for (StandardAtom at2 : a.precondition) {
                    if (at1.precedes(at2)) { //symetry breaking
                        if (atomMutexes.Contains(at1, at2)) {
                            isOk = false;
                            if (debug) {
                                System.out.print("[collision:"+at1+","+at2+"]");
                            }
                        }
                    }
                }
            }
            if (isOk) {
                if (debug) {
                    System.out.print(">Ok");
                }
                ret.actionList.add(a);
            } else {
                if (debug) {
                    System.out.print(">notOk");
                }
            }
        }
        return ret;
    }

    public void Construct() {
        //init

        {//extend the actions with no-ops
            for (StandardAtom a : atoms) {
                actions.add(new ActionInstance(a));
            }
        }

        {//setup P0
            AtomLayer al = new AtomLayer();
            al.facts.addAll(init);
            atomLayers.add(al);
            cumulativeAtomSet.addAll(al.facts);
        }

        {//setup P0mutex
            MutexAtoms ma = new MutexAtoms();
            atomMutexLayer.add(ma);
            //there are no mutex relations at the beginning
        }

        {//setup A0
            ActionLayer acl = new ActionLayer();
            actionLayers.add(acl);
            cumulativeActionSet.addAll(acl.actionList);
        }

        {//add A0mutex
            MutexActions mal = new MutexActions();
            actionMutexLayer.add(mal);
            //no ops can not be mutex
        }

        /*{//construct the mapping effect+ -> action instance
         for (ActionInstance a : cumulativeActionSet) {
         for (StandardAtom at : a.effectsPositive) {
         HashSet<ActionInstance> l = ef2act.get(at);
         if (l == null) {
         l = new HashSet<>();
         ef2act.put(at, l);
         }
         l.add(a);
         }
         }
         }*/

        //start the construction
        int lastAtomLayerSize = -1;
        int lastActionLayerSize = -1;
        int currentLvl = 0;
        boolean fixedPointReached = false;

        while (!fixedPointReached) {
            currentLvl++;

            {//add actions for the current level
                ActionLayer layer = allApplicableActions(atomMutexLayer.get(currentLvl - 1), cumulativeAtomSet, actions);
                actionLayers.add(layer);
                cumulativeActionSet.addAll(layer.actionList);
            }

            {//create the mapping effect+ -> action instance
                for (ActionInstance a : cumulativeActionSet) {
                    for (StandardAtom at : a.effectsPositive) {
                        HashSet<ActionInstance> l = ef2act.get(at);
                        if (l == null) {
                            l = new HashSet<>();
                            ef2act.put(at, l);
                        }
                        l.add(a);
                    }
                }
            }

            {//add atoms for the current layer
                AtomLayer atl = new AtomLayer();
                for (ActionInstance a : actionLayers.get(currentLvl).actionList) {
                    for (StandardAtom at : a.effectsPositive) {
                        if (at.name.equals("passenger-at(p0, n3)")) {
                        }
                        atl.facts.add(at);
                    }
                }
                atomLayers.add(atl);
                cumulativeAtomSet.addAll(atl.facts);
            }

            {//add mutex actions layer
                MutexActions mal = new MutexActions();
                for (ActionInstance a : cumulativeActionSet) {//actionLayers.get(currentLvl).actionList) { //new x
                    for (ActionInstance b : cumulativeActionSet) { //new join old
                        if (a.precedes(b)) {
                            if (a.name.equals("no-op lift-at(fast0, n3)") && b.name.equals("board(p0, fast0, n0, n0, n1)")) {
                            }
                            if (a.name.equals("board(p0, fast0, n3, n0, n1)") && b.name.equals("no-op lift-at(fast0, n3)")) {
                            }
                            if (!ActionInstance.ActionsAreIndependant(a, b) || ActionInstance.ActionsHaveCommonMutexPrecondition(a, b, atomMutexLayer.get(currentLvl - 1))) {
                                mal.set.add(new ActionPair(a, b));
                            }
                        }
                    }
                }
                actionMutexLayer.add(mal);
            }

            {//add atoms mutex layer
                MutexAtoms mat = new MutexAtoms();
                for (StandardAtom a : cumulativeAtomSet) {
                    for (StandardAtom b : cumulativeAtomSet) {
                        if (a.precedes(b)) {
                            if (a.name.equals("boarded(p0, fast0)") && b.name.equals("lift-at(fast0, n3)")) {
                            }
                            if (a.name.equals("lift-at(fast0, n3)") && b.name.equals("boarded(p0, fast0)")) {
                            }
                            HashSet<ActionInstance> one = ef2act.get(a);
                            HashSet<ActionInstance> two = ef2act.get(b);
                            if (one == null || two == null) {
                                continue; //effects that are not added by any action
                            }
                            boolean allColliding = true;
                            for (ActionInstance x : one) {
                                for (ActionInstance y : two) {
                                    //if (!x.equals(y) && !actionMutexLayer.get(currentLvl).set.contains(new ActionPair(x, y))) {
                                    if (!actionMutexLayer.get(currentLvl).set.contains(new ActionPair(x, y))) {
                                        allColliding = false;
                                    }
                                }
                            }
                            if (allColliding) {
                                if (a.name.equals("passenger-at(p0, n0)") && b.name.equals("passenger-at(p1, n0)")) {
                                }
                                if (a.name.equals("passenger-at(p1, n0)") && b.name.equals("passenger-at(p0, n0)")) {
                                }
                                mat.Add(a, b);
                            }
                        }
                    }
                }
                atomMutexLayer.add(mat);
            }

            {//check the termination condition
                fixedPointReached = (cumulativeAtomSet.size() == lastAtomLayerSize) && (cumulativeActionSet.size() == lastActionLayerSize);
                lastAtomLayerSize = cumulativeAtomSet.size();
                lastActionLayerSize = cumulativeActionSet.size();
            }
        }
        //done
    }

    public void RemoveNoOps() {
        LinkedHashSet<ActionInstance> acts = new LinkedHashSet<>();
        for (ActionInstance i : cumulativeActionSet) {
            if (i.name.startsWith("no-op")) {
                //do nothing
            } else {
                acts.add(i);
            }
        }
        cumulativeActionSet = acts;
    }

    public void RemoveStaticAtomOccurences() {
        //remove all static atoms
        LinkedHashSet<StandardAtom> atomsToRemove = new LinkedHashSet<>(atomLayers.get(0).facts);
        LinkedHashSet<StandardAtom> negatedAtoms = new LinkedHashSet<>();
        for (ActionInstance ai : cumulativeActionSet) {
            negatedAtoms.addAll(ai.effectsNegative);
        }
        //atoms that are in init, but not in negateAtoms are considered static - we do not ever change their value
        atomsToRemove.removeAll(negatedAtoms);
        cumulativeAtomSet.removeAll(atomsToRemove);
        for (ActionInstance ai : cumulativeActionSet) {
            ai.precondition.removeAll(atomsToRemove);
        }
        atomMutexLayer.get(atomMutexLayer.size() - 1).RemoveSubset(atomsToRemove);
        atomLayers.get(0).facts.removeAll(atomsToRemove);
        /*for(LinkedList<StandardAtom> ats:at){
         ats.removeAll(atomsToRemove);
         }*/
    }
}
