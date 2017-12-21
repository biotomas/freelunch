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
package freelunch.core.satSolving.symbolicReachability.walkreach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilityProblem;
import freelunch.core.satSolving.symbolicReachability.SymbolicReachabilitySolver;
import freelunch.core.satSolving.symbolicReachability.walkreach.model.ReachClause;
import freelunch.core.satSolving.symbolicReachability.walkreach.model.WalkieReachParamaters;
import freelunch.core.utilities.Stopwatch;


public class WalkieReachSolver implements SymbolicReachabilitySolver {
    
    private WalkieReachParamaters params;
    private WeightedWalkSat walksatSolver;
    private int signatureSize;
    private int searchMakespan;
    
    public WalkieReachSolver() {
        this(new WalkieReachParamaters());
    }
    
    public WalkieReachSolver(WalkieReachParamaters params) {
        this.params = params;
    }
    
    private void initializeWalksat(SymbolicReachabilityProblem problem, boolean includeInitAndGoal) {
        signatureSize = problem.initialConditions.getVariables();
        searchMakespan = params.getSearchMakespan();
        
        walksatSolver = new WeightedWalkSat(params.getSeed(), params.getRandomWalkProb());
        walksatSolver.initializeEmptyFormula(signatureSize * searchMakespan);
        
        // compute the clause weights
        int[] levelWeights = new int[searchMakespan];
        for (int i = 0; i < levelWeights.length; i++) {
            int distanceFromCenter = Math.abs(i - params.getSearchCenter());
            levelWeights[i] = 1 + distanceFromCenter * params.getWeightIncrease();
        }
        
        // fill in the clauses
        for (int[] uclauses : problem.universalConditions.getClauses()) {
            for (int i = 0; i < searchMakespan; i++) {
                walksatSolver.addClause(makeClause(uclauses, i*signatureSize, levelWeights[i]));
            }
        }
        for (int[] tclauses : problem.transitionConditions.getClauses()) {
            for (int i = 0; i+1 < searchMakespan; i++) {
                walksatSolver.addClause(makeClause(tclauses, i*signatureSize, (levelWeights[i]+levelWeights[i+1])/2));
            }
        }
        if (includeInitAndGoal) {
            for (int[] iclauses : problem.initialConditions.getClauses()) {
                walksatSolver.addClause(makeClause(iclauses, 0, levelWeights[0]));
            }
            for (int[] gclauses : problem.goalConditions.getClauses()) {
                walksatSolver.addClause(makeClause(gclauses, (searchMakespan-1)*signatureSize, levelWeights[searchMakespan-1]));
            }
        }
    }
    
    private ReachClause makeClause(int[] array, int constant, int weight) {
        int[] result = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length; i++) {
            int var = Math.abs(result[i]);
            var += constant;
            result[i] = result[i] > 0 ? var : -var;
        }        
        return new ReachClause(result, weight);
    }
    
    private Stopwatch watch;
    
    @Override
    public ArrayList<int[]> solve(SymbolicReachabilityProblem problem) {
        watch = new Stopwatch();
        startingSteps = new ArrayList<int[]>();
        endingSteps = new ArrayList<int[]>();
        int maxMakespan = 2 * params.getSearchMakespan();
        while (!watch.limitExceeded(params.getTimelimit())) {
            ArrayList<int[]> sol = trySolve(problem, maxMakespan);
            if (sol != null) {
                return sol;
            }
            maxMakespan++;
        }
        return null;
    }
    
    private List<int[]> startingSteps;
    private List<int[]> endingSteps;
    
    private ArrayList<int[]> trySolve(SymbolicReachabilityProblem problem, int maxMakespan) {
        // Phase I : initial walksat
        initializeWalksat(problem, true);
        boolean sat = walksatSolver.searchForSolution(params.getNoImprovementFlips(), true);
        if (sat) {
            return extractReachabilitySolution();
        }
        
        // Phase II : expanding
        startingSteps.clear();
        endingSteps.clear();
        walksatSolver.freezeLowerVars(signatureSize);
        walksatSolver.freezeHigherVars(signatureSize * (searchMakespan - 1));
        
        int makespan = params.getSearchMakespan();
        while (makespan < maxMakespan) {       
            int[] assignment = walksatSolver.getCurrentAssignment();
            
            // Save the assignment of the first and last frame            
            int[] first = getAssignmentForTime(assignment, 0);
            int[] last = getAssignmentForTime(assignment, searchMakespan-1);
            startingSteps.add(first);
            endingSteps.add(last);
                    
            //TODO verifikovat ze prve 2 a posledne 2 kroky su spravne.
            initializeWalksat(problem, false);
            assignment = walksatSolver.getCurrentAssignment();
            int[] second = getAssignmentForTime(assignment, 1);
            int[] preLast = getAssignmentForTime(assignment, searchMakespan - 2);
            setAssignmentForTime(assignment, second, 0);
            setAssignmentForTime(assignment, preLast, searchMakespan-1);
            sat = walksatSolver.searchForSolution(params.getNoImprovementFlips(), false);
            if (sat) {
                return extractReachabilitySolution();
            }
        }
        return extractReachabilitySolution();
    }
    
    private int[] getAssignmentForTime(int[] assignment, int time) {
        int[] tmp = new int[signatureSize+1]; 
        for (int i = 0; i < tmp.length; i++) {
            int diff = time*signatureSize;
            tmp[i] = assignment[i+diff] > 0 ? i : -i;
        }
        return tmp;
    }

    private void setAssignmentForTime(int[] assignment, int[] newAssignment, int time) {
        int start = signatureSize*time;
        int end = signatureSize*(time+1);
        
        for (int i = start; i < end; i++) {
            assignment[i] = newAssignment[i - start] > 0 ? i : -i ;            
        }
    }
   
    private ArrayList<int[]> extractReachabilitySolution() {
        ArrayList<int[]> result = new ArrayList<int[]>();
        for (int[] a : startingSteps) {
            result.add(a);
        }
        int[] assignment = walksatSolver.getCurrentAssignment();
        for (int i = 0; i < searchMakespan; i++) {
            result.add(getAssignmentForTime(assignment, i));
        }
        for (int[] a : endingSteps) {
            result.add(a);
        }
        return result;
    }

}
