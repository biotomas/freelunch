/*******************************************************************************
 * Copyright (c) 2012 Tomas Balyo and Vojtech Bardiovsky
 * 
 * This file is part of freeLunch
 * 
 * freeLunch is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 * 
 * freeLunch is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with freeLunch.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package freelunch.tutorials.wolfGoatCabbagePuzzle;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.model.StringActionInfo;
import freelunch.core.planning.sase.optimizer.PlanVerifier;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;

public class WGCExample {
    
    public static void main(String[] args) {
        WGCExample test = new WGCExample();
        test.testWGCExample();
    }

    private void testWGCExample() {
        // We generate the planning problem (see below)
        SasProblemBuilder problem = generateProblem();
        SasProblem sasProb = problem.getSasProblem();
        
        // We initialize the planner with the problem
        IncrementalSolver planner = new IncrementalSolver(sasProb);

        // We solve the problem
        try {
            SasParallelPlan plan = planner.solve();

            // print the plan
            System.out.println(plan);

            // Optionally we can verify the plan
            boolean valid = PlanVerifier.verifyPlan(sasProb, plan);
            if (valid) {
                System.out.println("Plan is VALID");
            } else {
                System.out.println("Plan in INVALID");
            }

        } catch (TimeoutException e) {
            System.out.println("Timeout occured");
        } catch (NonexistentPlanException e) {
            System.out.println("The planning problem has no solution");
        }
    }

    /**
     * Generate toy problem where Wolf Goat and Cabbage are transported across the river.
     * 
     * @return the planning problem
     */
    private SasProblemBuilder generateProblem() {
        // Initialize the planning problem
        SasProblemBuilder problem = new SasProblemBuilder();

        // ==========================
        // Create the state variables
        // ==========================
        
        // boat can be either on left side or right side of the river (0->left, 1->right)
        StateVariable boatLocation = problem.newVariable("boatLoc", 2);
        
        // boat can be empty or loaded with one item (0->empty, 1->W, 2->G, 3->C)
        StateVariable boatCargo = problem.newVariable("boatCargo", 4);
        
        // on each side of the river we can have 8 situations:
        // W G C
        // 1 1 1 -> 7   X
        // 1 1 0 -> 6   X
        // 1 0 1 -> 5   
        // 1 0 0 -> 4
        // 0 1 1 -> 3   X
        // 0 1 0 -> 2
        // 0 0 1 -> 1
        // 0 0 0 -> 0
        // 
        // In states marked with X boat is forbidden to leave. 
        StateVariable[] sideSituation = new StateVariable[2];
        sideSituation[0] = problem.newVariable("leftSideState", 8);
        sideSituation[1] = problem.newVariable("rightSideState", 8);
        
        // ================================
        // Define the initial state
        // ================================

        
        //boat is on the left side
        problem.addInitialStateCondition(new Condition(boatLocation,0));
        
        //boat is empty (no cargo)
        problem.addInitialStateCondition(new Condition(boatCargo,0));
        
        //everything is on the left side
        problem.addInitialStateCondition(new Condition(sideSituation[0],7));
        
        //nothing is on the right side
        problem.addInitialStateCondition(new Condition(sideSituation[1],0));
        
        // ==============================
        // Define the actions (operators)
        // ==============================
        
        // actions are named in following fashion:
        // addLoad<item>_<sideSituation before>_<sideSituation after>_Action
        // addUnload<item>_<sideSituation before>_<sideSituation after>_Action
        for (int side = 0; side <= 1; side++) {
            this.addLoadWolf_7_3_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadWolf_6_2_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadWolf_5_1_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadWolf_4_0_Action(problem, boatLocation, boatCargo, sideSituation, side);
            
            this.addLoadGoat_7_5_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadGoat_6_4_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadGoat_3_1_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadGoat_2_0_Action(problem, boatLocation, boatCargo, sideSituation, side);
            
            this.addLoadCabbage_7_6_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadCabbage_5_4_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadCabbage_3_2_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addLoadCabbage_1_0_Action(problem, boatLocation, boatCargo, sideSituation, side);
            
            this.addUnloadWolf_3_7_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadWolf_2_6_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadWolf_1_5_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadWolf_0_4_Action(problem, boatLocation, boatCargo, sideSituation, side);
            
            this.addUnloadGoat_5_7_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadGoat_4_6_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadGoat_1_3_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadGoat_0_2_Action(problem, boatLocation, boatCargo, sideSituation, side);
            
            this.addUnloadCabbage_6_7_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadCabbage_4_5_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadCabbage_2_3_Action(problem, boatLocation, boatCargo, sideSituation, side);
            this.addUnloadCabbage_0_1_Action(problem, boatLocation, boatCargo, sideSituation, side);
        }
        
        int right = 1;
        int left = 0;
        
        // actions are named in following fashion:
        // addMoveBoat_<sideSituation on actual side where the boat is>_Action
        this.addMoveBoat_5_Action(problem, boatLocation, sideSituation, left);
        this.addMoveBoat_4_Action(problem, boatLocation, sideSituation, left);
        this.addMoveBoat_2_Action(problem, boatLocation, sideSituation, left);
        this.addMoveBoat_1_Action(problem, boatLocation, sideSituation, left);
        this.addMoveBoat_0_Action(problem, boatLocation, sideSituation, left);
        
        this.addMoveBoat_5_Action(problem, boatLocation, sideSituation, right);
        this.addMoveBoat_4_Action(problem, boatLocation, sideSituation, right);
        this.addMoveBoat_2_Action(problem, boatLocation, sideSituation, right);
        this.addMoveBoat_1_Action(problem, boatLocation, sideSituation, right);
        this.addMoveBoat_0_Action(problem, boatLocation, sideSituation, right);
        
        // =================================
        // Define the goal state
        // =================================
        
        // we do not care about anything else than situation on the right side of the river
        // we want everything to be transported there -> situation 7
        problem.addGoalCondition(new Condition(sideSituation[1],7));
       
        return problem;
    }
    
    // Actions to move boat - it is only possible to leave the riverbank if  sideSituation= 5 or 4 or 2 or 1 or 0
    // in this way goat and cabbage would be safe:
    //
    // situation =  W G C
    // ------------------
    //      5    =  1 0 1
    //      4    =  1 0 0
    //      2    =  0 1 0
    //      1    =  0 0 1
    //      0    =  0 0 0
    private void addMoveBoat_5_Action(SasProblemBuilder problem, StateVariable boatLocation, StateVariable[] sideSituation, int start) {
        // decide about origin and destination side of the river
        int orig = start;
        int dest = (start + 1) % 2;
        // Create a new operator named like Move-boat:1->0
        SasAction op = problem.newAction(new StringActionInfo(String.format("Move-boat5:%d->%d", orig, dest)));
        
        // add the preconditions
        
        // boat must be there
        op.getPreconditions().add(new Condition(boatLocation, start));
        
        // situation on this side of the river has to be safe
        op.getPreconditions().add(new Condition(sideSituation[start], 5));
        
        // add the effects
        op.getEffects().add(new Condition(boatLocation,dest));
    }
    
    private void addMoveBoat_4_Action(SasProblemBuilder problem, StateVariable boatLocation, StateVariable[] sideSituation, int start) {
        // decide about origin and destination side of the river
        int orig = start;
        int dest = (start + 1) % 2;
        // Create a new operator named like Move-boat:1->0
        SasAction op = problem.newAction(new StringActionInfo(String.format("Move-boat4:%d->%d", orig, dest)));
        
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, start));
        op.getPreconditions().add(new Condition(sideSituation[start], 4));
        
        // add the effects
        op.getEffects().add(new Condition(boatLocation,dest));
    }
    
    private void addMoveBoat_2_Action(SasProblemBuilder problem, StateVariable boatLocation, StateVariable[] sideSituation, int start) {
        // decide about origin and destination side of the river
        int orig = start;
        int dest = (start + 1) % 2;
        // Create a new operator named like Move-boat:1->0
        SasAction op = problem.newAction(new StringActionInfo(String.format("Move-boat2:%d->%d", orig, dest)));
        
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, start));
        op.getPreconditions().add(new Condition(sideSituation[start], 2));
        
        // add the effects
        op.getEffects().add(new Condition(boatLocation,dest));
    }
    
    private void addMoveBoat_1_Action(SasProblemBuilder problem, StateVariable boatLocation, StateVariable[] sideSituation, int start) {
        // decide about origin and destination side of the river
        int orig = start;
        int dest = (start + 1) % 2;
        // Create a new operator named like Move-boat:1->0
        SasAction op = problem.newAction(new StringActionInfo(String.format("Move-boat1:%d->%d", orig, dest)));
        
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, start));
        op.getPreconditions().add(new Condition(sideSituation[start], 1));
        
        // add the effects
        op.getEffects().add(new Condition(boatLocation,dest));
    }
    
    private void addMoveBoat_0_Action(SasProblemBuilder problem, StateVariable boatLocation, StateVariable[] sideSituation, int start) {
        // decide about origin and destination side of the river
        int orig = start;
        int dest = (start + 1) % 2;
        // Create a new operator named like Move-boat:1->0
        SasAction op = problem.newAction(new StringActionInfo(String.format("Move-boat0:%d->%d", orig, dest)));
        
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, start));
        op.getPreconditions().add(new Condition(sideSituation[start], 0));
        
        // add the effects
        op.getEffects().add(new Condition(boatLocation,dest));
    }
    
    // Actions for loading Wolf
    private void addLoadWolf_7_3_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-wolf73-at-%d", where)));
        // add the preconditions
        
        // boat must be on this side
        op.getPreconditions().add(new Condition(boatLocation, where));
        
        // boat must be empty
        op.getPreconditions().add(new Condition(boatCargo,0));
        
        // wolf has to be there
        op.getPreconditions().add(new Condition(sideSituation[where], 7));
        
        // add the effects
        
        // wolf is in the boat boatCargo = 1 (1 is for Wolf)
        op.getEffects().add(new Condition(boatCargo, 1));
        
        // situation on this side is changed - there is no wolf anymore
        op.getEffects().add(new Condition(sideSituation[where],3));
    }
    
    private void addLoadWolf_6_2_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-wolf62-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 6));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 1));
        op.getEffects().add(new Condition(sideSituation[where],2));
    }
    
        private void addLoadWolf_5_1_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-wolf51-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 5));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 1));
        op.getEffects().add(new Condition(sideSituation[where],1));
    }
        
        private void addLoadWolf_4_0_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-wolf40-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 4));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 1));
        op.getEffects().add(new Condition(sideSituation[where],0));
    }
        
    // Actions for loading Goat
    private void addLoadGoat_7_5_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-goat75-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 7));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 2));
        op.getEffects().add(new Condition(sideSituation[where], 5));
    }
    
    private void addLoadGoat_6_4_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-goat64-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 6));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 2));
        op.getEffects().add(new Condition(sideSituation[where], 4));
    }
    
    private void addLoadGoat_3_1_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-goat31-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 3));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 2));
        op.getEffects().add(new Condition(sideSituation[where], 1));
    }
    
    private void addLoadGoat_2_0_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-goat20-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 2));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 2));
        op.getEffects().add(new Condition(sideSituation[where], 0));
    }
    
    // Actions for loading Cabbage
    private void addLoadCabbage_7_6_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-cabbage76-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 7));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 3));
        op.getEffects().add(new Condition(sideSituation[where], 6));
    }
    
    private void addLoadCabbage_5_4_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-cabbage54-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 5));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 3));
        op.getEffects().add(new Condition(sideSituation[where], 4));
    }
    
    private void addLoadCabbage_3_2_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-cabbage32-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 3));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 3));
        op.getEffects().add(new Condition(sideSituation[where], 2));
    }
    
    private void addLoadCabbage_1_0_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Load-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Load-cabbage10-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,0));
        op.getPreconditions().add(new Condition(sideSituation[where], 1));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 3));
        op.getEffects().add(new Condition(sideSituation[where], 0));
    }
    
     // Actions for unloading Wolf
    private void addUnloadWolf_3_7_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-wolf37-at-%d", where)));
        // add the preconditions
        
        // boat must be on this side
        op.getPreconditions().add(new Condition(boatLocation, where));
        
        // wolf is in the boat boatCargo = 1 (1 is for Wolf)
        op.getPreconditions().add(new Condition(boatCargo, 1));
        
        // situation on this side
        op.getPreconditions().add(new Condition(sideSituation[where],3));
        
        // add the effects
        
        
        op.getEffects().add(new Condition(boatCargo, 0));
        
        // situation on this side is changed
        op.getEffects().add(new Condition(sideSituation[where],7));
    }
    
    private void addUnloadWolf_2_6_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-wolf26-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,1));
        op.getPreconditions().add(new Condition(sideSituation[where], 2));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where],6));
    }
    
        private void addUnloadWolf_1_5_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-wolf15-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,1));
        op.getPreconditions().add(new Condition(sideSituation[where], 1));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where],5));
    }
        
        private void addUnloadWolf_0_4_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-wolf-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-wolf04-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,1));
        op.getPreconditions().add(new Condition(sideSituation[where], 0));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where],4));
    }
        
    // Actions for Unloading Goat
    private void addUnloadGoat_5_7_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-goat57-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,2));
        op.getPreconditions().add(new Condition(sideSituation[where], 5));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 7));
    }
    
    private void addUnloadGoat_4_6_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-goat46-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,2));
        op.getPreconditions().add(new Condition(sideSituation[where], 4));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where],6));
    }
    
    private void addUnloadGoat_1_3_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-goat13-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,2));
        op.getPreconditions().add(new Condition(sideSituation[where],1));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 3));
    }
    
    private void addUnloadGoat_0_2_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-goat-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-goat02-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,2));
        op.getPreconditions().add(new Condition(sideSituation[where], 0));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 2));
    }
    
    // Actions for Unloading Cabbage
    private void addUnloadCabbage_6_7_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-cabbage67-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,3));
        op.getPreconditions().add(new Condition(sideSituation[where], 6));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 7));
    }
    
    private void addUnloadCabbage_4_5_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-cabbage45-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,3));
        op.getPreconditions().add(new Condition(sideSituation[where], 4));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 5));
    }
    
    private void addUnloadCabbage_2_3_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-cabbage23-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,3));
        op.getPreconditions().add(new Condition(sideSituation[where], 2));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 3));
    }
    
    private void addUnloadCabbage_0_1_Action(SasProblemBuilder problem, StateVariable boatLocation,
                                        StateVariable boatCargo, StateVariable[] sideSituation, int where) {
        // Create a new operator named like Unload-cabbage-at-1
        SasAction op = problem.newAction(new StringActionInfo(String.format("Unload-cabbage01-at-%d", where)));
        // add the preconditions
        op.getPreconditions().add(new Condition(boatLocation, where));
        op.getPreconditions().add(new Condition(boatCargo,3));
        op.getPreconditions().add(new Condition(sideSituation[where], 0));
        
        // add the effects
        op.getEffects().add(new Condition(boatCargo, 0));
        op.getEffects().add(new Condition(sideSituation[where], 1));
    }
}
