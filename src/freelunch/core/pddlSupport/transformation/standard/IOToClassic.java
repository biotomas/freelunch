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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
public class IOToClassic {

    private static Atom createAtom(String name, String[] vars) {
        Atom at = new Atom();
        if (vars.length == 0) {
            at.mName = name + "()";
        } else {
            at.mName = name + "(";
            for (String s : vars) {
                at.mName += s + ",";
            }
            at.mName = at.mName.substring(0, at.mName.length() - 1);
            at.mName += ")";
        }
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

    private static Atom createAtom(String name, LinkedList<IOVariable> vars, HashMap<String, String> varInstances) {
        String[] vrs = new String[vars.size()];
        int ct = 0;
        for (IOVariable v : vars) {
            if (v.mName.startsWith("?")) {
                vrs[ct++] = varInstances.get(v.mName);
            } else {
                vrs[ct++] = v.mName;
            }
        }
        return createAtom(name, vrs);
    }

    private static Action createAction(IOAction a, String[] currentVars) {
        Action ac = new Action();
        ac.mName = a.mName + "(";
        //create map varID -> actual variable
        HashMap<String, String> varInstances = new HashMap<>();
        int ct = 0;
        for (IOVariable var : a.parameters) {
            varInstances.put(var.mName, currentVars[ct++]);
        }
        for (IOLiteral l : a.conditions) {
            Atom at = createAtom(l.mName, l.vars, varInstances);
            if (!l.positive) {
                throw new UnsupportedOperationException("Sorry, no support for negative preconditions yet.");
            }
            ac.preconditions.add(at);
        }
        for (IOLiteral l : a.effects) {
            Atom at = createAtom(l.mName, l.vars, varInstances);
            if (l.positive) {
                ac.positiveEffects.add(at);
            } else {
                ac.negativeEffects.add(at);
            }
        }
        for (IOVariable s : a.parameters) {
            ac.mName += varInstances.get(s.mName) + ",";
        }
        if (a.parameters.size() > 0) {
            ac.mName = ac.mName.substring(0, ac.mName.length() - 1);
        }

        ac.mName += ")";
        ac.mCost = a.cost;
        return ac;
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


        {//ground the predicates
            for (IOPredicate p : dm.predicates) {
                String[] currentVars = new String[p.mVars.size()];
                List<Iterator<IOVariable>> its = new ArrayList<>();
                for (IOVariable v : p.mVars) {
                    its.add(typeToObjectMap.get(v.mType).iterator());
                }
                int currentLevel = 0;
                while (currentLevel >= 0) {
                    if (currentLevel == its.size()) {
                        //the end of the sequence, we create an atom and go back
                        Atom at = createAtom(p.mName, currentVars);
                        t.atoms.add(at);
                        currentLevel--; //go back
                    } else {
                        if (!its.get(currentLevel).hasNext()) {//reset and go back
                            its.set(currentLevel, typeToObjectMap.get(p.mVars.get(currentLevel).mType).iterator());
                            currentLevel--;
                        } else { //set the current value
                            currentVars[currentLevel] = its.get(currentLevel).next().mName;
                            currentLevel++; //go forward if we are not at the end
                        }
                    }
                }
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

        {//ground the actions -- THIS IS ERROURNEOUS, since actions without any parameters wont be passed through
            for (IOAction a : dm.actions) {
                String[] currentVars = new String[a.parameters.size()];
                List<Iterator<IOVariable>> its = new ArrayList<>();
                for (IOVariable v : a.parameters) {
                    its.add(typeToObjectMap.get(v.mType).iterator());
                }
                int currentLevel = 0;
                while (currentLevel >= 0) {
                    if (currentLevel == its.size()) {
                        //the end of the sequence, we create an action and go back
                        Action ac = createAction(a, currentVars);
                        t.actions.add(ac);
                        currentLevel--; //go back
                    } else {
                        if (!its.get(currentLevel).hasNext()) {//reset and go back
                            its.set(currentLevel, typeToObjectMap.get(a.parameters.get(currentLevel).mType).iterator());
                            currentLevel--;
                        } else { //set the current value
                            currentVars[currentLevel] = its.get(currentLevel).next().mName;
                            currentLevel++; //go forward if we are not at the end
                        }
                    }
                }
            }
        }
        return t;
    }
}
