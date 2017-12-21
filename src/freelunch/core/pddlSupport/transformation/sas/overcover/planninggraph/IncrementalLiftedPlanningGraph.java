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
package freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import freelunch.core.pddlSupport.io.pddl.in.objects.IOAction;
import freelunch.core.pddlSupport.io.pddl.in.objects.IODomain;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOLiteral;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOPredicate;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOProblem;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOType;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.ActionInstance;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.Operator;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.Type;

/**
 *
 * @author Filip Dvořák
 */
public class IncrementalLiftedPlanningGraph {

    int lastAtomLayerSize;
    int lastActionLayerSize;
    LinkedList<ActionLayer> actionLayers = new LinkedList<>();
    LinkedList<AtomLayer> atomLayers = new LinkedList<>();
    LinkedList<MutexActions> actionMutexLayer = new LinkedList<>();
    public LinkedList<MutexAtoms> atomMutexLayer = new LinkedList<>();
    LinkedHashSet<StandardAtom> staticAtoms;
    //useful variables
    public LinkedHashSet<StandardAtom> cumulativeAtomSet = new LinkedHashSet<>();
    public LinkedHashSet<ActionInstance> cumulativeActionSet = new LinkedHashSet<>();
    HashMap<StandardAtom, HashSet<ActionInstance>> ef2act; //cumulative map of pozitive effect -> action instance
    LinkedList<IOVariable> variables;
    LinkedList<StandardAtom> atoms;
    LinkedList<Type> types;
    HashMap<String, LinkedList<IOVariable>> typeToObjectMap = new HashMap<>();
    HashMap<String, String> objectToTypeMap = new HashMap<>();
    LinkedList<Operator> operators;
    public LinkedList<StandardAtom> init;
    public LinkedList<StandardAtom> goal;
    HashMap<IOPredicate, LinkedList<Operator>> predicate2OperatorCondition;

    public void Explain() {
    }

    public void Init(IOProblem pr, IODomain dom) {
        lastAtomLayerSize = -1;
        lastActionLayerSize = -1;

        {//join problem's objects with domain's constants -TODO, is this the correct behaviour? Afaik, it should not matter ...
            variables = new LinkedList<>(pr.objects);
            variables.addAll(dom.constants);
        }

        {//translate types
            types = new LinkedList<>();
            types.add(new Type("object", null)); //those are default
            for (IOType t1 : dom.types) {
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
                for (int j = 0; j < variables.size(); j++) {
                    if (types.get(i).IsDerivedFromMe(variables.get(j).mType)) {
                        vs.add(variables.get(j));
                    }
                }
                typeToObjectMap.put(types.get(i).mName, vs);
            }
        }

        {//create map object -> type
            objectToTypeMap = new HashMap<>();
            for (IOVariable v : variables) {
                objectToTypeMap.put(v.mName, v.mType);
            }
        }

        {//prepare operators for grounding - adds lists of all possible objects to parameters of the operator
            operators = new LinkedList<>();
            for (IOAction a : dom.actions) {
                LinkedList<LinkedList<IOVariable>> l = new LinkedList<>();
                if (a.parameters != null) {
                    for (IOVariable v : a.parameters) {
                        l.add(typeToObjectMap.get(v.mType));
                    }
                }
                operators.add(new Operator(l, a.parameters, a));
            }
        }

        {//generate initial and goal atoms, those we will need for sure ...
            init = new LinkedList<>();
            goal = new LinkedList<>();

            //add init predicates
            for (IOPredicate pred : pr.predicateInit) {
                for (IOVariable v : pred.mVars) {
                    v.mType = objectToTypeMap.get(v.mName);
                }
                init.add(new StandardAtom(pred.mVars, false, pred.mName));
            }

            for (IOLiteral g : pr.goal) {
                LinkedList<IOVariable> vrs = new LinkedList<>();
                for (IOVariable v : g.vars) {
                    vrs.add(new IOVariable(v.mName, objectToTypeMap.get(v.mName)));
                }
                goal.add(new StandardAtom(vrs, false, g.mName));
            }
        }

        {//create mapping predicate -> operator
            predicate2OperatorCondition = new HashMap<>();
            for (IOPredicate p : dom.predicates) {
                LinkedList<Operator> l = new LinkedList<>();
                for (Operator o : operators) {
                    if (o.ContainsInCondition(p)) {
                        l.add(o);
                    }
                }
                predicate2OperatorCondition.put(p, l);
            }
        }

        {//initialize the planning graph



            //initial atom layer
            //first find all static predicates/functions
            /*staticAtoms = new LinkedHashSet<>();
             HashSet<IOPredicate> staticWrappers = new HashSet<>();
             for (IOPredicate fpw : dom.predicates) {
             boolean isStatic = true;
             for (Operator o : operators) {
             for (IOLiteral f : o.effectsPositive) {
             if (fpw.IsDerivedFromMe(f)) {
             isStatic = false;
             }
             }
             for (IOLiteral f : o.effectsNegative) {
             if (fpw.IsDerivedFromMe(f)) {
             isStatic = false;
             }
             }
             }
             if (isStatic) {
             fpw.isStatic = true;
             staticWrappers.add(fpw);
             } else {
             fpw.isStatic = false;
             }
             }*/

            AtomLayer al = new AtomLayer();
            for (StandardAtom a : init) {
                /* REWRITE THIS!!
                 if (FPWrapper.IsDerivedAsStatic(a, fpwrapper)) {
                 staticAtoms.add(a);
                 } else {
                 al.facts.add(a);
                 }
                 * */
                al.facts.add(a);
            }
            atomLayers.add(al);
            cumulativeAtomSet = new LinkedHashSet<>(al.facts);

            //add the no-op actions for the initial atoms
            ActionLayer acl = new ActionLayer();
            for (StandardAtom a : init) {
                acl.actionList.add(new ActionInstance(a));
            }
            actionLayers.add(acl);

            //add mutex atoms layer
            MutexAtoms mat = new MutexAtoms();
            atomMutexLayer.add(mat);

            //add mutex actions layer
            MutexActions mal = new MutexActions();
            actionMutexLayer.add(mal);
        }
        ef2act = new HashMap<>();

        {//incrementaly update the effect+ -> action instance
            for (ActionInstance a : actionLayers.get(0).actionList) {
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
    }

    public boolean ExpandPlanningGraph(int currentLvl) {

        {//add actions for the current level
            ActionLayer layer = AllApplicableActions(atomMutexLayer.get(currentLvl - 1), cumulativeAtomSet, atomLayers.get(currentLvl - 1).facts);
            actionLayers.add(layer);
        }

        {//add atoms for the current layer
            AtomLayer atl = new AtomLayer();
            for (ActionInstance a : actionLayers.get(currentLvl).actionList) {
                for (StandardAtom at : a.effectsPositive) {
                    //if (!cumulativeAtomSet.contains(at)) {
                    atl.facts.add(at);
                    //}
                }
            }
            atomLayers.add(atl);
        }

        {//aggregate atoms and actions into the cummulative layer
            for (StandardAtom a : atomLayers.get(currentLvl).facts) {
                cumulativeAtomSet.add(a);
            }
            for (ActionInstance a : actionLayers.get(currentLvl).actionList) {
                cumulativeActionSet.add(a);
            } //now the cumulative layer contains all the actions and atoms that should be shown on this level in the classic planning graph semantic
        }

        {//add mutex actions layer
            MutexActions mal = new MutexActions();
            for (ActionInstance a : cumulativeActionSet) {//actionLayers.get(currentLvl).actionList) { //new x
                for (ActionInstance b : cumulativeActionSet) { //new join old
                    if (!a.equals(b)) { //TODO those for cycles can be reduced in half with smarter structure
                        if (!ActionInstance.ActionsAreIndependant(a, b) || ActionInstance.ActionsHaveCommonMutexPrecondition(a, b, atomMutexLayer.get(currentLvl - 1))) {
                            mal.set.add(new ActionPair(a, b));
                        }
                    }
                }
            }
            actionMutexLayer.add(mal);
        }

        {//incrementaly update the mapping effect+ -> action instance
            for (ActionInstance a : actionLayers.get(currentLvl).actionList) {
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

        {//add atoms mutex layer
            MutexAtoms mat = new MutexAtoms();
            for (StandardAtom a : cumulativeAtomSet) {
                for (StandardAtom b : cumulativeAtomSet) {
                    if (a.precedes(b)) {
                        HashSet<ActionInstance> one = ef2act.get(a);
                        HashSet<ActionInstance> two = ef2act.get(b);
                        boolean allColliding = true;
                        for (ActionInstance x : one) {
                            for (ActionInstance y : two) {
                                if (!x.equals(y) && !actionMutexLayer.get(currentLvl).set.contains(new ActionPair(x, y))) {
                                    allColliding = false;
                                }
                            }
                        }
                        if (allColliding) {
                            mat.Add(a, b);
                        }
                    }
                }
            }
            atomMutexLayer.add(mat);
        }
        boolean fixedPoint = (cumulativeAtomSet.size() == lastAtomLayerSize) && (cumulativeActionSet.size() == lastActionLayerSize);
        lastAtomLayerSize = cumulativeAtomSet.size();
        lastActionLayerSize = cumulativeActionSet.size();
        return !fixedPoint; //fixed point reached ?
    }

    private void InstantiatePartiallySpecifiedOperator(Operator op, HashMap<Integer, IOVariable> chosenVars, LinkedHashSet<ActionInstance> set, LinkedHashSet<StandardAtom> allPreviousAtoms, MutexAtoms atomMutexes) {
        if (chosenVars.size() == op.parameters.size()) {
            //create atoms from chosen parameter variables for all Facts in the operator
            int ct = 0;
            LinkedList<IOVariable> cv = new LinkedList<>();
            while (chosenVars.containsKey(ct)) {
                cv.add(chosenVars.get(ct));
                ct++;
            }
            //we need to check, if the action's conditions are satisfied
            ActionInstance ai = new ActionInstance(op, cv);
            if (ai.ApplicableAction(atomMutexes, allPreviousAtoms, staticAtoms)) {
                set.add(ai);
            }

        } else {
            int index = -1;
            for (int i = 0; i < op.parameters.size(); i++) {
                if (!chosenVars.containsKey(i)) {
                    index = i;
                }
            }
            LinkedList<IOVariable> vars = op.parameterInstances.get(index);
            for (IOVariable v : vars) {
                HashMap<Integer, IOVariable> o2 = new HashMap<>(chosenVars);
                o2.put(index, v);
                InstantiatePartiallySpecifiedOperator(op, o2, set, allPreviousAtoms, atomMutexes);
            }
        }
    }

    private ActionLayer AllApplicableActions(MutexAtoms atomMutexes, LinkedHashSet<StandardAtom> allPreviousAtoms, LinkedHashSet<StandardAtom> newAtoms) {
        ActionLayer ret = new ActionLayer();
        //allPreviousAtoms already contains new atoms!!!
        for (StandardAtom a : newAtoms) {
            for (Operator o : operators) {
                boolean validAtom = false; //is the atom a valid one for this operator ?
                IOLiteral concernedCondition = null;
                for (IOLiteral f : o.preconditions) {
                    if (a.originName.equals(f.mName)) {
                        boolean valid = true;
                        for (int i = 0; i < a.mOrigins.size(); i++) {
                            if (!a.mOrigins.get(i).mType.equals(f.vars.get(i).mType)) {
                                valid = false;
                            }
                        }
                        if (valid) {
                            validAtom = true;
                            concernedCondition = f;
                        }
                    }
                }
                if (!validAtom) {
                    //do nothing
                } else {
                    //find all strictly compatible conditions (types must match w/o inheritance) -this might be a problem for badly defined domains
                    //also, we match only one condition - same as above, we expect well defined domain
                    HashMap<Integer, IOVariable> chosenVars = new HashMap<>();
                    for (int i = 0; i < concernedCondition.vars.size(); i++) {
                        String varName = concernedCondition.vars.get(i).mName;
                        String varType = concernedCondition.vars.get(i).mType;
                        String object = a.mOrigins.get(i).mName;
                        int index = -1;
                        for (int j = 0; j < o.parameters.size(); j++) {
                            if (o.parameters.get(j).mName.equals(varName)) {
                                index = j;
                            }
                        }
                        chosenVars.put(index, new IOVariable(object, varType));
                    }
                    InstantiatePartiallySpecifiedOperator(o, chosenVars, ret.actionList, allPreviousAtoms, atomMutexes);
                }
            }
        }
        //lets add all no-ops
        for (StandardAtom a : allPreviousAtoms) {
            ret.actionList.add(new ActionInstance(a));
        }
        return ret;
    }
}
