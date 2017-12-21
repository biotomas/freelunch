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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.io.pddl.in.objects.IOAction;
import freelunch.core.pddlSupport.io.pddl.in.objects.IODomain;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOLiteral;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOPredicate;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOProblem;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOType;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;
import freelunch.core.pddlSupport.representations.classic.Action;
import freelunch.core.pddlSupport.representations.classic.Atom;
import freelunch.core.pddlSupport.representations.classic.ClassicGroundTask;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.Type;

/**
 *
 * @author FD
 */
public class IOToCLassic_KBReduction {

    private static Atom createAtom(String name, String[] vars) {
        Atom at = new Atom();
        at.mName = name + "(";
        for (String s : vars) {
            at.mName += s + ",";
        }
        at.mName = at.mName.substring(0, at.mName.length() - 1);
        at.mName += ")";
        return at;
    }

    private static Atom createAtom(String name, LinkedList<IOVariable> vars) {
        String[] vrs = new String[vars.size()];
        int ct = 0;
        for (IOVariable v : vars) {
            vrs[ct++] = v.mName;
        }
        return createAtom(name, vrs);
    }

    private static Atom createAtom(String name, HashMap<IOVariable, IOVariable> varInstances, LinkedList<IOVariable> vars) {
        String[] vrs = new String[vars.size()];
        int ct = 0;
        for (IOVariable v : vars) {
            if (v.mName.startsWith("?")) {
                for (IOVariable var : varInstances.keySet()) {
                    if (v.mName.equals(var.mName)) {
                        vrs[ct++] = varInstances.get(var).mName;
                    }
                }

            } else {
                vrs[ct++] = v.mName;
            }
        }
        return createAtom(name, vrs);
    }

    public static ClassicGroundTask Transform(IOProblem pr, IODomain dm) {
        if (!pr.domainName.equals(dm.domainName)) {
            throw new UnsupportedOperationException("The problem does not seem to belong to the domain given the names.");
        }

        //name the task
        ClassicGroundTask t = new ClassicGroundTask();
        t.mName = dm.domainName + ":" + pr.problemName;

        //aux vars
        List<IOVariable> objects = new LinkedList<>();
        List<Type> types = new LinkedList<>();
        HashMap<String, LinkedList<IOVariable>> typeToObjectMap = new HashMap<>();
        HashMap<String, String> objectToTypeMap = new HashMap<>();

        {//gather objects in the problem
            objects.addAll(pr.objects);
            objects.addAll(dm.constants);
        }

        {//translate types
            types.add(new Type("object", null)); //those are default
            for (IOType t1 : dm.types) {
                boolean found = false;
                for (Type t2 : types) {
                    if (t1.mType.equals(t2.mName)) {
                        types.add(new Type(t1.mName, t2));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    throw new UnsupportedOperationException("Unknown type");
                }
            }
        }

        {//create map type -> object
            for (int i = 0; i < types.size(); i++) {
                LinkedList<IOVariable> vs = new LinkedList<>();
                for (int j = 0; j < objects.size(); j++) {
                    if (types.get(i).IsDerivedFromMe(objects.get(j).mType)) {
                        vs.add(objects.get(j));
                    }
                }
                typeToObjectMap.put(types.get(i).mName, vs);
            }
        }

        {//create map object -> type
            for (IOVariable v : pr.objects) {
                objectToTypeMap.put(v.mName, v.mType);
            }
        }

        {//generate initial and goal atoms, those we will need for sure ...

            //add init predicates
            for (IOPredicate pred : pr.predicateInit) {
                for (IOVariable v : pred.mVars) {
                    v.mType = objectToTypeMap.get(v.mName);
                }
                Atom a = createAtom(pred.mName, pred.mVars);
                t.init.add(a);
            }

            for (IOLiteral g : pr.goal) {
                LinkedList<IOVariable> vrs = new LinkedList<>();
                for (IOVariable v : g.vars) {
                    vrs.add(new IOVariable(v.mName, objectToTypeMap.get(v.mName)));
                }
                Atom a = createAtom(g.mName, vrs);
                t.goal.add(a);
            }
        }

        {//KB grounding
            List<IOAction> ops = dm.actions;
            LinkedHashSet<Atom> kb = new LinkedHashSet<>(t.init);
            /*HashMap<String, LinkedList<String>> floatingTypeObjectMap = new HashMap<>();
             for(String tp:typeToObjectMap.keySet()){
             floatingTypeObjectMap.put(tp, new LinkedList<String>());
             }*/
            LinkedHashSet<Action> appliedActions = new LinkedHashSet<>();
            LinkedList<Atom> newAtoms = new LinkedList<>(t.init);
            int oldSize = -1;
            while (oldSize != kb.size()) {
                oldSize = kb.size();
                LinkedList<Atom> newerAtoms = new LinkedList<>();
                LinkedList<Action> newerActions = new LinkedList<>();
                //now generate the next layer of applicable actions and atoms
                for (Atom at : newAtoms) {
                    for (IOAction ac : ops) {
                        List<Action> acts = instantiateOperatorByNewAtom(ac, at, kb, typeToObjectMap);
                        newerActions.addAll(acts);
                    }
                }
                for (Action a : newerActions) {
                    for (Atom pos : a.positiveEffects) {
                        if (!kb.contains(pos)) {
                            newerAtoms.add(pos);
                        }
                    }
                }
                appliedActions.addAll(newerActions);
                kb.addAll(newerAtoms);
            }

            if (!triviallySatisfiable(kb, t.goal)) {
                throw new UnsupportedOperationException("The planning task failed the test of relaxed reachability of the goal.");
            }

            ClassicGroundTask ret = new ClassicGroundTask();
            ret.init = t.init;
            ret.goal = t.goal;
            ret.actions = new LinkedList<>(appliedActions);
            ret.atoms = new LinkedList<>(kb);
            ret.mName = t.mName + "//ClassicToClassic_KBReductionNaive";
            return ret;
        }
    }

    private static List<Action> instantiateOperatorByNewAtom(IOAction op, Atom newAtom, LinkedHashSet<Atom> kb, HashMap<String, LinkedList<IOVariable>> typeToObjectMap) {
        LinkedHashSet<Action> ret = new LinkedHashSet<>();
        //get the conditions of the operator that unify with the newAtom
        List<IOLiteral> hitLiterals = new LinkedList<>();
        for (IOLiteral lit : op.conditions) {
            if (lit.GetPredicateName().equals(newAtom.GetPredicateName())) {
                hitLiterals.add(lit);
            }
        }
        /*if (hitLiterals.size() == -1) {//if there is just one
         HashMap<IOVariable, IOVariable> subs = new HashMap<>();
         Iterator<IOVariable> it1 = hitLiterals.get(0).vars.iterator();
         Iterator<IOVariable> it2 = newAtom.GetObjectNames().iterator();
         HashSet<String> chosenVars = new HashSet<>();
         while (it1.hasNext()) {
         IOVariable one = it1.next(), two = it2.next();
         subs.put(one, two);
         chosenVars.add(one.mName);
         }
         for (IOVariable v : op.parameters) {
         if (!chosenVars.contains(v.mName)) {
         subs.put(v, null);
         }
         }
         ret.addAll(instantiateOpRec(op, kb, subs, typeToObjectMap));
         } else */
        if (hitLiterals.size() > 0) {
            for (IOLiteral li : hitLiterals) {
                HashMap<IOVariable, IOVariable> subs = new HashMap<>();
                Iterator<IOVariable> it1 = li.vars.iterator();
                Iterator<IOVariable> it2 = newAtom.GetObjectNames().iterator();
                HashSet<String> chosenVars = new HashSet<>();
                while (it1.hasNext()) {
                    IOVariable one = it1.next(), two = it2.next();
                    subs.put(one, two);
                    chosenVars.add(one.mName);
                }
                for (IOVariable v : op.parameters) {
                    if (!chosenVars.contains(v.mName)) {
                        subs.put(v, null);
                    }
                }
                ret.addAll(instantiateOpRec(op, kb, subs, typeToObjectMap));
            }
        } else {
            //hitLiterals.size() == 0
            //do nothing
        }
        return new LinkedList<>(ret);
    }

    public static List<Action> instantiateOpRec(IOAction op, LinkedHashSet<Atom> kb, HashMap<IOVariable, IOVariable> subs, HashMap<String, LinkedList<IOVariable>> typeToObjectMap) {
        List<Action> ret = new LinkedList<>();
        if (!subs.containsValue(null)) {
            Action na = createNewAction(op, subs, kb);
            if (na != null) {
                ret.add(na); //add new action for this substitution, if we can
            }

        } else {
            //find unfullfilled substitution and iterate through its possible assignements
            for (IOVariable k : subs.keySet()) {
                if (subs.get(k) == null) {
                    for (IOVariable v : typeToObjectMap.get(k.mType)) {
                        subs.put(k, v);
                        ret.addAll(instantiateOpRec(op, kb, subs, typeToObjectMap));
                        subs.put(k, null);
                    }
                }
            }
        }
        return ret;
    }

    private static boolean triviallySatisfiable(HashSet<Atom> kb, List<Atom> goal) {
        for (Atom at : goal) {
            if (!kb.contains(at)) {
                return false;
            }
        }
        return true;
    }

    private static Action createNewAction(IOAction op, HashMap<IOVariable, IOVariable> subs, LinkedHashSet<Atom> kb) {
        //first create all condition atoms and always check, if they are in the current knowledge base
        //then create eveything else in the action and create even the atoms if they do not exist
        List<Atom> preconditions = new LinkedList<>();
        for (IOLiteral l : op.conditions) {
            Atom at = createAtom(l.mName, subs, l.vars);
            if (!kb.contains(at)) {
                return null;
            } else {
                preconditions.add(at);
            }
        }
        //so now we know, that the action will exist and we create everything else
        Action ac = new Action();
        ac.preconditions = preconditions;
        ac.mName = op.mName + "(";
        for (IOLiteral l : op.conditions) {
            if (!l.positive) {
                throw new UnsupportedOperationException("Sorry, no support for negative preconditions yet.");
            }
            Atom at = createAtom(l.mName, subs, l.vars);
            if (!kb.contains(at)) {
                return null;
            } else {
                preconditions.add(at);
            }
        }
        for (IOLiteral l : op.effects) {
            Atom at = createAtom(l.mName, subs, l.vars);
            if (l.positive) {
                ac.positiveEffects.add(at);
            } else {
                ac.negativeEffects.add(at);
            }
        }
        for (IOVariable s : op.parameters) {
            for (IOVariable ss : subs.keySet()) {
                if (s.mName.equals(ss.mName)) {
                    ac.mName += subs.get(ss).mName + ",";
                }
            }
        }
        /*
         for (IOVariable s : subs.values()) {
         ac.mName += s.mName + ",";
         }*/
        if (subs.size() > 0) {
            ac.mName = ac.mName.substring(0, ac.mName.length() - 1);
        }

        ac.mName += ")";
        ac.mCost = op.cost;
        return ac;

    }
}
