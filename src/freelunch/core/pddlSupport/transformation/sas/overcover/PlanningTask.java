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
package freelunch.core.pddlSupport.transformation.sas.overcover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import freelunch.core.pddlSupport.io.pddl.in.PDDLFactory;
import freelunch.core.pddlSupport.io.pddl.in.objects.IODomain;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOProblem;
import freelunch.core.pddlSupport.io.pddl.in.objects.IOVariable;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.ActionInstance;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.Operator;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.StandardAtom;
import freelunch.core.pddlSupport.transformation.sas.overcover.entities.Type;
import freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph.IncrementalLiftedPlanningGraph;
import freelunch.core.pddlSupport.transformation.sas.overcover.planninggraph.MutexAtoms;

/**
 *
 * @author Filip Dvořák
 */
public class PlanningTask {

    public long timeTranslation, timePlanningGraph, timeSetCover;
    String domainName, problemName;
    ArrayList<Type> types;
    ArrayList<Operator> operators;
    LinkedList<StandardAtom> atoms;
    LinkedList<ActionInstance> actionInstances;
    LinkedHashSet<StandardAtom> init;
    LinkedHashSet<StandardAtom> goals;
    LinkedHashSet<StandardAtom> finalAtoms;
    LinkedHashSet<ActionInstance> finalActions;
    ArrayList<IOVariable> variables;
    HashMap<String, ArrayList<IOVariable>> typeToObjectMap;
    HashMap<String, String> objectToTypeMap;
    LinkedList<LinkedList<StandardAtom>> mutexSets;

    /*public ArrayList<ArrayList<StandardAtom>> GetMutexSetsGreedy() {
        LinkedList<LinkedList<StandardAtom>> rt2 = SetCovering.greedySetCover(mutexSets);

        ArrayList<ArrayList<StandardAtom>> ret = new ArrayList<ArrayList<StandardAtom>>();
        for (LinkedList<StandardAtom> a : rt2) {
            ArrayList<StandardAtom> set = new ArrayList<StandardAtom>();
            ret.add(set);
            for (StandardAtom at : a) {
                set.add(at.GetAtom());
            }
        }
        System.out.println("Mutex set covering using Greedy had following results: " + ret.size() + " mutex sets.");
        return ret;
    }*/

    /*public ArrayList<ArrayList<Atom>> GetMutexSetsSmartN1() {
     LinkedList<LinkedList<StandardAtom>> rt = SetCovering.smartSetCoverN1(mutexSets, finalAtoms);

     ArrayList<ArrayList<Atom>> ret = new ArrayList<ArrayList<Atom>>();
     for (LinkedList<StandardAtom> a : rt) {
     ArrayList<Atom> set = new ArrayList<Atom>();
     ret.add(set);
     for (StandardAtom at : a) {
     set.add(at.GetAtom());
     }
     }
     System.out.println("Mutex set overcovering using SmartN1 had following results: " + ret.size() + " mutex sets.");
     return ret;
     }*/
    @SuppressWarnings("unused")
    private void instantiateOperator(int whatVariable, Operator op, LinkedList<IOVariable> chosenVars) {
        if (whatVariable == op.parameterInstances.size()) {
            //create atoms from chosen parameter variables for all Facts in the operator
            actionInstances.add(new ActionInstance(op, chosenVars));
        } else {
            LinkedList<IOVariable> vars = op.parameterInstances.get(whatVariable);
            for (IOVariable v : vars) {
                LinkedList<IOVariable> o2 = new LinkedList<>(chosenVars);
                o2.add(v);
                instantiateOperator(whatVariable + 1, op, o2);
            }
        }

    }

    private boolean fits(MutexAtoms mAtoms, LinkedList<StandardAtom> currentSet, StandardAtom one) {
        boolean ret = true;
        for (StandardAtom a : currentSet) {
            if (!mAtoms.Contains(a, one)) {
                ret = false;
            }
        }
        return ret;
    }

    private void NaiveMutexSearch(MutexAtoms mAtoms, LinkedList<StandardAtom> currentSet, LinkedHashSet<StandardAtom> candidates) {
        ArrayList<StandardAtom> newCandidates = new ArrayList<>();
        for (StandardAtom at : candidates) {
            if (fits(mAtoms, currentSet, at)) {
                newCandidates.add(at);
            }
        }
        if (newCandidates.isEmpty()) {
            if (currentSet.size() > 0) {
                mutexSets.add(currentSet);
            }
        } else {
            for (StandardAtom at : newCandidates) {
                LinkedList<StandardAtom> newCurrent = new LinkedList<>(currentSet);
                LinkedHashSet<StandardAtom> newNewCandidates = new LinkedHashSet<>(newCandidates);
                Iterator<StandardAtom> it = newNewCandidates.iterator();
                boolean done = false;
                while (!done) {
                    StandardAtom atom = it.next();
                    it.remove();
                    if (atom == at) {
                        done = true;
                    }
                }
                newCurrent.add(at);
                NaiveMutexSearch(mAtoms, newCurrent, newNewCandidates);
            }
        }
    }

    /**
     * Parses domain and problem from given paths, joins, grounds further
     * filters them
     *
     * @param domainPath
     * @param problemPath
     */
    public PlanningTask(String domainPath, String problemPath) {
        IOProblem p = PDDLFactory.parseProblem(problemPath);
        IODomain d = PDDLFactory.parseDomain(domainPath);

        p.Explain(System.out);
        d.Explain(System.out);
        
        if (!p.domainName.equals(d.domainName)) {
            throw new UnsupportedOperationException("The problem is defined for a different domain.");
        }

        problemName = p.problemName;
        domainName = p.domainName;


        IncrementalLiftedPlanningGraph pg = new IncrementalLiftedPlanningGraph();
        pg.Init(p, d);
        int cta = 1;
        while (pg.ExpandPlanningGraph(cta)) {
            cta++;
        }

        //join objects with constants
        variables = new ArrayList<>(p.objects);
        for (int i = 0; i < d.constants.size(); i++) {
            variables.add(d.constants.get(i));
        }
        //transfer problem definition
        finalAtoms = new LinkedHashSet<>(pg.cumulativeAtomSet);
        finalActions = new LinkedHashSet<>(pg.cumulativeActionSet);
        init = new LinkedHashSet<>(pg.init);
        goals = new LinkedHashSet<>(pg.goal);

        //translate mutexes
        /*int mSize = finalAtoms.size();
         boolean[][] mutex = new boolean[mSize][];
         for(int i = 0; i < mSize; i++){
         mutex[i] = new boolean[mSize];
         }*/


        //remove implicitly satisfied goals and all atoms that are invariant to the planning problem
        //LinkedHashSet<StandardAtom> interestingAtoms = new LinkedHashSet<>();
        //LinkedHashSet<StandardAtom> alwaysTrueAtoms = new LinkedHashSet<>();
        LinkedHashSet<StandardAtom> deletedAtoms = new LinkedHashSet<>();
        LinkedHashSet<StandardAtom> addedAtoms = new LinkedHashSet<>();
        LinkedHashSet<StandardAtom> atomsForRemoval = new LinkedHashSet<>();

        //remove no-ops
        {
            ArrayList<ActionInstance> toRemove = new ArrayList<>();
            for (ActionInstance a : finalActions) {
                if (a.name.startsWith("no-op")) {
                    toRemove.add(a);
                }
            }
            finalActions.removeAll(toRemove);
        }

        //find all atoms whose truth value can be turned false/true by an action
        for (ActionInstance a : finalActions) {
            for (StandardAtom at : a.effectsNegative) {
                deletedAtoms.add(at);
            }
            for (StandardAtom at : a.effectsPositive) {
                addedAtoms.add(at);
            }
        }
        //find atoms that are always true or false
        for (StandardAtom at : finalAtoms) {
            if (init.contains(at) && !deletedAtoms.contains(at)) {
                atomsForRemoval.add(at);
            }
            if (!init.contains(at) && !addedAtoms.contains(at)) {
                //this should be always prevented by the planning graph structure
                throw new UnsupportedOperationException("Invalid operation in this context.");
            }
        }
        //remove the atoms from the problem

        goals.removeAll(atomsForRemoval);
        init.removeAll(atomsForRemoval);
        finalAtoms.removeAll(atomsForRemoval);
        for (ActionInstance a : finalActions) {
            a.precondition.removeAll(atomsForRemoval);
        }
        MutexAtoms mutAtoms = pg.atomMutexLayer.get(pg.atomMutexLayer.size() - 1);
        mutAtoms.RemoveSubset(atomsForRemoval);
        mutexSets = new LinkedList<>();
        NaiveMutexSearch(mutAtoms, new LinkedList<StandardAtom>(), finalAtoms);


        /**
         * prints out histogram of mutex sizes
         */
        System.out.println("Mutex sets histogram follows (size:count)");
        int[] pl = new int[mutexSets.size()];
        for (LinkedList<StandardAtom> l : mutexSets) {
            pl[l.size()]++;
        }
        for (int i = 0; i < pl.length; i++) {
            if (pl[i] > 0) {
                System.out.println(i + ": " + pl[i]);
            }
        }

    }

    /*private enum ECommandLineArgument {

        NONE, d, p, o, b
    }
*/
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        String domainPath = null, problemPath = null, outputPath = null;
        if (args.length == 4) {
            ECommandLineArgument state = ECommandLineArgument.NONE;

            for (String s : args) {
                switch (state) {
                    case NONE:
                        state = ECommandLineArgument.valueOf(s.substring(1));
                        break;
                    case d:
                        domainPath = s;
                        state = ECommandLineArgument.NONE;
                        break;
                    case o:
                        outputPath = s;
                        state = ECommandLineArgument.NONE;
                        break;
                    case p:
                        problemPath = s;
                        state = ECommandLineArgument.NONE;
                        break;
                }
            }
        }


        //domainPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc2/elevator/domain.pddl"; problemPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc2/elevator/p-09-4/prob.pddl";
        //domainPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/depots/domain.pddl"; problemPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/depots/p01/prob.pddl";
        //domainPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/driverlog/domain.pddl"; problemPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/driverlog/p10/prob.pddl";
        //domainPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc5/rovers/domain.pddl"; problemPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc5/rovers/p13/prob.pddl";
        //domainPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc5/tpp/domain.pddl"; problemPath = "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc5/tpp/p01/prob.pddl";
        domainPath = "D:/_MFF/_PhD/_EXPERIMENTY/2011_OverCoverTesting/coverDomains/pegsol/domain.pddl";
        problemPath = "D:/_MFF/_PhD/_EXPERIMENTY/2011_OverCoverTesting/coverDomains/pegsol/p03/p03.pddl";

*/


        /**
         * testing
         */
        //GroundPlanningProblem ppp = elevators.GetPlanningProblem();
        //TODO parsing of the domains below
        //brackets !!!
        //PlanningTask openstacks = new PlanningTask("D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc5/openstacks/p01/domain.pddl", "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc5/openstacks/p01/prob.pddl");
        //only constants ???
        //PlanningTask airport = new PlanningTask("D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc4/airport/p01-cond/domain.pddl", "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc4/airport/p01-cond/prob.pddl");
        //contains type joins
        //PlanningTask zenotravel = new PlanningTask("D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/zenotravel/domain.pddl", "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/zenotravel/p01/prob.pddl");
        //too large to ground naively
        //PlanningTask freecell = new PlanningTask("D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/freecell/domain.pddl", "D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/freecell/p01/prob.pddl");
        //int a = 0;


        /*
         String ar[] = problemPath.split("/");
         String problem = ar[ar.length - 1];
         ar = domainPath.split("/");
         String domain = ar[ar.length - 1];
         System.out.println("Translating: " + domain + ":" + problem);
         long time = System.currentTimeMillis();
         PlanningTask t = new PlanningTask(domainPath, problemPath);
         GroundPlanningProblem p = t.GetPlanningProblem();
         for (GroundAction ai : p.getGroundActions()) {
         System.out.println(ai.getName());
         }
         System.out.println(p);
         ArrayList<ArrayList<Atom>> ms2 = t.GetMutexSetsGreedy();
         ArrayList<ArrayList<Atom>> ms = t.GetMutexSetsSmartN1();
         time = System.currentTimeMillis() - time;
         System.out.println("Processed: " + p.getProblemName());
         System.out.println("Object fluents: " + p.getGroundObjectFluents().size());
         System.out.println("Predicates: " + p.getGroundPredicates().size());
         System.out.println("Actions: " + p.getGroundActions().size());
         System.out.println("Time, total: " + time / 1000f + "s");
         System.out.println("Time, translation: " + t.timeTranslation / 1000f + "s");
         System.out.println("Time, planningGraph: " + t.timePlanningGraph / 1000f + "s");
         System.out.println("Time, setCover: " + t.timeSetCover / 1000f + "s");
         System.out.println("--------------------------------------------------------");


         int ct = 0;
         SASReader r = new SASReader(problemPath.replace("prob.pddl", "output.sas"));//"D:/_PROJECTS/_RUKSVN/domains/planningProblems/ipc3/driverlog/p11/output.sas");
         for (String s : r.names) {
         boolean found = false;
         for (GroundAction g : p.getGroundActions()) {
         if (g.getName().equals(s)) {
         found = true;
         }
         }

         if (!found) {
         ct++;
         }
         }
         */
    //}
}
