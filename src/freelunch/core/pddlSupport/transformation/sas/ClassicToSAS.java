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
package freelunch.core.pddlSupport.transformation.sas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import freelunch.core.pddlSupport.representations.SAS.SASTask;
import freelunch.core.pddlSupport.representations.SAS.SAction;
import freelunch.core.pddlSupport.representations.SAS.SAssignment;
import freelunch.core.pddlSupport.representations.SAS.SValue;
import freelunch.core.pddlSupport.representations.SAS.SVariable;
import freelunch.core.pddlSupport.representations.classic.Action;
import freelunch.core.pddlSupport.representations.classic.Atom;
import freelunch.core.pddlSupport.representations.classic.ClassicGroundTask;
import freelunch.core.pddlSupport.transformation.sas.overcover.CliqueSearch;
import freelunch.core.pddlSupport.transformation.sas.overcover.SetCovering;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.ActionInstance;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;
import freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph.PlanningGraph;

/**
 *
 * @author FD
 */
public class ClassicToSAS {

    private static PlanningGraph prepareThePlanningGraph(ClassicGroundTask in) {
        //preparing the planning graph
        PlanningGraph g = new PlanningGraph();
        for (Atom a : in.init) {
            g.init.add(new StandardAtom(a.mName));
        }
        for (Atom a : in.goal) {
            g.goal.add(new StandardAtom(a.mName));
        }
        for (Atom a : in.atoms) {
            g.atoms.add(new StandardAtom(a.mName));
        }
        for (Action a : in.actions) {
            List<StandardAtom> precons = new LinkedList<>();
            for (Atom at : a.preconditions) {
                precons.add(new StandardAtom(at.mName));
            }
            List<StandardAtom> posEffects = new LinkedList<>();
            for (Atom at : a.positiveEffects) {
                posEffects.add(new StandardAtom(at.mName));
            }
            List<StandardAtom> negEffects = new LinkedList<>();
            for (Atom at : a.negativeEffects) {
                negEffects.add(new StandardAtom(at.mName));
            }
            g.actions.add(new ActionInstance(precons, posEffects, negEffects, a.mName, a.mCost));
        }

        //lets construct the graph
        g.Construct();
        g.RemoveNoOps();
        g.RemoveStaticAtomOccurences();

        //System.out.println("Removed atoms: " + (in.atoms.size() - g.cumulativeAtomSet.size()));
        //System.out.println("Removed actions: " + (in.actions.size() - g.cumulativeActionSet.size()));

        return g;
    }

    private static void printHistogram(LinkedList<LinkedList<StandardAtom>> someMS) {
        int n = - 1;
        for (LinkedList<StandardAtom> l : someMS) {
            n = Math.max(n, l.size());
        }
        int[] pl = new int[n + 1];
        for (LinkedList<StandardAtom> l : someMS) {
            pl[l.size()]++;
        }
        for (int i = 0; i < pl.length; i++) {
            if (pl[i] > 0) {
                System.out.println(i + ": " + pl[i]);
            }
        }
    }

    public static SASTask TransformTryOut(ClassicGroundTask in) {
        PlanningGraph g = prepareThePlanningGraph(in);

        //lets find some mutex sets        
        LinkedList<LinkedList<StandardAtom>> mutexSets = CliqueSearch.GetMutexSetsLimited(g.atomMutexLayer.get(g.atomMutexLayer.size() - 1), g.cumulativeAtomSet, 5000);
        LinkedList<LinkedList<StandardAtom>> greedyMS = SetCovering.greedySetCover(mutexSets);
        LinkedList<LinkedList<StandardAtom>> smartMS = SetCovering.smartSetCoverN1(mutexSets, g.cumulativeAtomSet);

        System.out.println("Mutex sets histogram follows (size:count)");
        printHistogram(mutexSets);
        System.out.println("greedyMS histogram follows (size:count)");
        printHistogram(greedyMS);
        System.out.println("smartMS histogram follows (size:count)");
        printHistogram(smartMS);

        SASTask task = new SASTask();


        return task;
    }

    public static SASTask TransformGreedyCover(ClassicGroundTask in) {
        PlanningGraph g = prepareThePlanningGraph(in);
        //lets find some mutex sets
        LinkedList<LinkedList<StandardAtom>> mutexSets = CliqueSearch.GetMutexSetsLimited(g.atomMutexLayer.get(g.atomMutexLayer.size() - 1), g.cumulativeAtomSet, 5000);
        LinkedList<LinkedList<StandardAtom>> greedyMS = SetCovering.greedySetCover(mutexSets);
        /*System.out.println("Mutex sets histogram follows (size:count)");
         printHistogram(mutexSets);
         System.out.println("greedyMS histogram follows (size:count)");
         printHistogram(greedyMS);
         */
        SASTask task = new SASTask();
        //lets give me some name
        task.mName = in.mName + "//ClassicToSAS.TransformGreedyCover";
        createSASTask(greedyMS, task, g, in);
        return task;
    }
    
    public static SASTask TransformGreedyCoverWithLeftOvers(ClassicGroundTask in) {
        PlanningGraph g = prepareThePlanningGraph(in);
        //lets find some mutex sets
        LinkedList<LinkedList<StandardAtom>> mutexSets = CliqueSearch.GetMutexSetsLimited(g.atomMutexLayer.get(g.atomMutexLayer.size() - 1), g.cumulativeAtomSet, 5000);
        LinkedList<LinkedList<StandardAtom>> greedyMS = SetCovering.greedySetCoverWithLeftOvers(mutexSets);
        /*System.out.println("Mutex sets histogram follows (size:count)");
         printHistogram(mutexSets);
         System.out.println("greedyMS histogram follows (size:count)");
         printHistogram(greedyMS);
         */
        SASTask task = new SASTask();
        //lets give me some name
        task.mName = in.mName + "//ClassicToSAS.TransformGreedyCover";
        createSASTask(greedyMS, task, g, in);
        return task;
    }

    private static void createSASTask(LinkedList<LinkedList<StandardAtom>> mutexSets, SASTask task, PlanningGraph g, ClassicGroundTask in) {
        int ct = 0;
        for (LinkedList<StandardAtom> var : mutexSets) {
            SVariable newVar = new SVariable();
            newVar.mName = "var" + (ct++);
            for (StandardAtom a : var) {
                SValue val = new SValue();
                val.mName = a.name;
                newVar.domain.add(val);
            }
            SValue val = new SValue();
            val.mName = "<none of those>" + newVar.mName;
            newVar.domain.add(val);
            task.variables.add(newVar);
        }
        //create mappings .. values (represented by strings) to its variables (in whose domain they are)
        HashMap<String, List<SVariable>> mp = new HashMap<>();
        for (SVariable v : task.variables) {
            for (SValue val : v.domain) {
                List<SVariable> l = mp.get(val.mName);
                if (l == null) {
                    l = new LinkedList<>();
                }
                l.add(v);
                mp.put(val.mName, l);
            }
        }
        //get init
        HashSet<String> varNames = new HashSet<>();
        for (StandardAtom at : g.atomLayers.get(0).facts) {
            List<SVariable> l = mp.get(at.name);
            for (SVariable var : l) {
                SAssignment ass = new SAssignment();
                SValue v = new SValue();
                v.mName = at.name;
                ass.val = v;
                ass.var = var;
                task.init.add(ass);
                varNames.add(ass.var.mName);
            }
        }
        //check - only holds for greedy covering
        /*int cnt = task.init.size();
         for(SAssignment v:task.init){
         cnt -= mp.get(v.val.mName).size();
         }
         if(cnt != 0){
         throw new UnsupportedOperationException("Invalid init.");
         }*/

        //get goal
        varNames = new HashSet<>();
        for (Atom a : in.goal) {
            List<SVariable> l = mp.get(a.mName);
            try {
                for (SVariable var : l) {
                    SAssignment ass = new SAssignment();
                    SValue v = new SValue();
                    v.mName = a.mName;
                    ass.val = v;
                    ass.var = var;
                    task.goal.add(ass);
                    varNames.add(ass.var.mName);
                }
            } catch (Exception e) {
            }
        }
        for (ActionInstance a : g.cumulativeActionSet) {
            SAction ac = new SAction();
            ac.mName = a.name;
            ac.mCost = a.cost;
            //preconditions
            for (StandardAtom at : a.precondition) {
                List<SVariable> l = mp.get(at.name);
                for (SVariable var : l) {
                    SAssignment ass = new SAssignment();
                    ass.var = var;
                    SValue val = new SValue();
                    val.mName = at.name;
                    ass.val = val;
                    ac.preconditions.add(ass);
                }
            }
            //effects
            for (StandardAtom at : a.effectsPositive) {
                List<SVariable> l = mp.get(at.name);
                for (SVariable var : l) {
                    SAssignment ass = new SAssignment();
                    ass.var = var;
                    SValue val = new SValue();
                    val.mName = at.name;
                    ass.val = val;
                    ac.effects.add(ass);
                }
            }
            //negative effects
            //try to check, if there exists some atom in positive effects, that is mutex with me, if it does, i dont have to do anything, if it does not, i have a problem ...
            for (StandardAtom at1 : a.effectsNegative) {
                //we need to explain what ahppens with the negative effect - it can be omited in following scenarios:
                // 1) there exists a positive effect that is a value in the same state variable as is the negative effect
                boolean allIsFine = false;
                for (StandardAtom at2 : a.effectsPositive) {
                    /*StandardAtom ats1 = new StandardAtom(at1.name);
                     StandardAtom ats2 = new StandardAtom(at2.name);*/
                    List<SVariable> intersection = new LinkedList<>(mp.get(at2.name));
                    intersection.retainAll(mp.get(at1.name));
                    if (!intersection.isEmpty()) {
                        allIsFine = true;
                    }
                }
                if (!allIsFine) {
                    //this means, that we have to find other way how to represent this negative effect
                    //lets look, if there is other value in the state variable of our value that can be achieved by this value, if it is, lets assign it ... we do not consider undefined values at all
                    List<SVariable> vars = new LinkedList<>(mp.get(at1.name));
                    SVariable var = vars.get(0);
                    if (var.domain.size() == 2) {
                        //we choose the other value in this domain
                        SAssignment ass = new SAssignment();
                        ass.var = var;
                        ass.val = var.domain.get(1);
                        ac.effects.add(ass);
                    } else {
                        //this is still ok .. we just assign "none of those" value
                        SAssignment ass = new SAssignment();
                        ass.var = var;
                        ass.val = var.domain.get(var.domain.size()-1);
                        ac.effects.add(ass);
                    }
                }
            }
            task.actions.add(ac);
        }
    }

    public static SASTask TransformSmartCover(ClassicGroundTask in) {
        PlanningGraph g = prepareThePlanningGraph(in);
        //lets find some mutex sets
        LinkedList<LinkedList<StandardAtom>> mutexSets = CliqueSearch.GetMutexSetsLimited(g.atomMutexLayer.get(g.atomMutexLayer.size() - 1), g.cumulativeAtomSet, 5000);
        LinkedList<LinkedList<StandardAtom>> smartMS = SetCovering.smartSetCoverN1(mutexSets, g.cumulativeAtomSet);
        /*System.out.println("Mutex sets histogram follows (size:count)");
         printHistogram(mutexSets);
         System.out.println("greedyMS histogram follows (size:count)");
         printHistogram(greedyMS);
         */
        SASTask task = new SASTask();
        //lets give me some name
        task.mName = in.mName + "//ClassicToSAS.TransformGreedyCover";
        createSASTask(smartMS, task, g, in);
        return task;
    }
}
