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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.io.pddl.in.objects.IOLiteral;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;
import freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph.MutexAtoms;

/**
 *
 * @author Filip Dvořák
 */
public class ActionInstance {

    private static HashMap<String, Integer> indexMap = new HashMap<>();

    public static void ResetPooling() {
        indexMap = new HashMap<>();
    }

    public void ResetHmax() {
        hMaxSupporter = null;
        hMaxVal = Float.POSITIVE_INFINITY;
    }
    public StandardAtom hMaxSupporter;
    public float hMaxVal;
    public int cost;
    public String name;
    public List<IOVariable> params = new LinkedList<>();
    public List<StandardAtom> precondition = new LinkedList<>();
    public List<StandardAtom> effectsPositive = new LinkedList<>();
    public List<StandardAtom> effectsNegative = new LinkedList<>();
    public int mIndex;

    public ActionInstance(List<StandardAtom> precons, List<StandardAtom> postEffects, List<StandardAtom> negEffects, String nm, int cost_) {
        cost = cost_;
        name = nm;
        precondition = precons;
        effectsNegative = negEffects;
        effectsPositive = postEffects;
        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }

    /**
     * creating a no-op
     *
     * @param a
     */
    public ActionInstance(StandardAtom a) {
        name = "no-op " + a.name;
        precondition.add(a);
        effectsPositive.add(a);

        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }

    public ActionInstance(Operator op, LinkedList<IOVariable> chosenVars) {
        params = chosenVars;
        HashMap<String, IOVariable> map = new HashMap<>();
        int ct = 0;
        for (IOVariable v : op.parameters) {
            map.put(v.mName, chosenVars.get(ct++));
        }

        for (IOLiteral f : op.preconditions) {
            LinkedList<IOVariable> newVars = new LinkedList<>();
            for (IOVariable v : f.vars) {
                newVars.add(map.get(v.mName));
            }
            precondition.add(new StandardAtom(newVars, false, f.mName));
        }

        for (StandardAtom a : effectsPositive) {
            if (a.functionalOrigin) { //we need to ad inverse effect of the function
                for (StandardAtom b : precondition) {
                    if (b.FunctionMatched(a)) {
                        effectsNegative.add(b);
                    }
                }
            }
        }

        params = chosenVars;
        name = op.mName + " ";
        for (IOVariable v : chosenVars) {
            name += v.mName + " ";
        }
        name = name.substring(0, name.length() - 1);

        if (indexMap == null) {
            indexMap = new HashMap<>();
        }
        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }

    /**
     *
     * @param kb
     * @return
     */
    public boolean IsApplicable(HashSet<String> kb) {
        boolean applicable = true;
        for (StandardAtom a : precondition) {
            if (!kb.contains(a.name)) {
                applicable = false;
            }
        }
        return applicable;
    }

    public boolean ApplicableAction(MutexAtoms mut, LinkedHashSet<StandardAtom> facts, LinkedHashSet<StandardAtom> staticFacts) {
        if (!IsApplicableClean(facts, staticFacts)) {
            return false;
        }
        for (StandardAtom a : precondition) {
            for (StandardAtom b : precondition) {
                if (!a.equals(b)) {
                    if (mut.Contains(a, b)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean ActionsHaveCommonMutexPrecondition(ActionInstance a, ActionInstance b, MutexAtoms mut) {
        for (StandardAtom x : a.precondition) {
            for (StandardAtom y : b.precondition) {
                if (!x.equals(y)) {
                    if (mut.Contains(x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean ActionsAreIndependant(ActionInstance a, ActionInstance b) {
        for (StandardAtom ef : a.effectsNegative) {
            for (StandardAtom x : b.precondition) {
                if (x.equals(ef)) {
                    return false;
                }
            }
            for (StandardAtom x : b.effectsPositive) {
                if (x.equals(ef)) {
                    return false;
                }
            }
        }
        for (StandardAtom ef : b.effectsNegative) {
            for (StandardAtom x : a.precondition) {
                if (x.equals(ef)) {
                    return false;
                }
            }
            for (StandardAtom x : a.effectsPositive) {
                if (x.equals(ef)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean IsApplicableClean(HashSet<StandardAtom> kb) {
        boolean applicable = true;
        for (StandardAtom a : precondition) {
            if (!kb.contains(a)) {
                applicable = false;
            }
        }
        return applicable;
    }

    public boolean IsApplicableClean(HashSet<StandardAtom> kb, HashSet<StandardAtom> kb2) {
        boolean applicable = true;
        for (StandardAtom a : precondition) {
            if (!kb.contains(a) && !kb2.contains(a)) {
                applicable = false;
            }
        }
        return applicable;
    }

    boolean ContainsInConditions(StandardAtom get) {
        for (StandardAtom a : precondition) {
            if (get.equals(a)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append('\n');
        sb.append(name);
        /*sb.append(params);
         sb.append("\nconds: ");
         sb.append(conditions);
         sb.append("\n pos: ");
         sb.append(positiveEffects);
         sb.append("\n neg: ");
         sb.append(negativeEffects);*/
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return mIndex == ((ActionInstance) obj).mIndex;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + mIndex;
        return hash;
    }

    public boolean precedes(ActionInstance b) {
        return mIndex < b.mIndex;
    }

    public boolean HasCondition(StandardAtom atom) {
        for (StandardAtom a : precondition) {
            if (a.equals(atom)) {
                return true;
            }
        }
        return false;
    }

    public void ReIndex() {
        if (!indexMap.containsKey(name)) {
            indexMap.put(name, indexMap.size());
        }
        mIndex = indexMap.get(name);
    }
}
