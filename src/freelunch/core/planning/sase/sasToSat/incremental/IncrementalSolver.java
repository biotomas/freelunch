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
package freelunch.core.planning.sase.sasToSat.incremental;

import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.translator.SasToSatTranslator;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslator;
import freelunch.core.satSolving.IncrementalSatSolver;
import freelunch.core.satSolving.Sat4JSolver;
import freelunch.core.satSolving.SatContradictionException;
import freelunch.core.utilities.Stopwatch;

public class IncrementalSolver implements Solver {

    private final IncrementalSatSolver solver;
    private final Stopwatch stopwatch;
    private final Stopwatch solveTime;
    private final Stopwatch totalSolveTime;
    private final SasToSatTranslator translator;
    private boolean timeoutOccurred;
    private final IncrementalSolverSettings settings;

    /**
     * Create a new Incremental solver with the given problem and default
     * settings
     * 
     * @param problem
     */
    public IncrementalSolver(SasProblem problem) {
        this(problem, new IncrementalSolverSettings());
    }
    
    public IncrementalSolver(SasProblem problem, IncrementalSatSolver solver, IncrementalSolverSettings settings) {
        this.settings = settings;
        stopwatch = new Stopwatch();
        this.solver = solver;
        // incremental solver does not work with the improved goal encoding
        settings.getTranslationSettings().setUseOriginalGoalEncoding(true);
        translator = new SaseTranslator(problem, this.settings.getTranslationSettings());
        solveTime = new Stopwatch();
        totalSolveTime = new Stopwatch();
        timeoutOccurred = false;
    }

    /**
     * Create a new Incremental solver with the given problem and settings
     * 
     * @param problem
     */
    public IncrementalSolver(SasProblem problem, IncrementalSolverSettings settings) {
        this(problem, new Sat4JSolver(), settings);
    }

    @Override
    public SasParallelPlan solve() throws NonexistentPlanException, TimeoutException {
        int timespan = 0;
        solver.setVariablesCount(translator.setMaxTimespan(timespan));
        totalSolveTime.reset();
        boolean satisfiable;
        int timelimit = settings.getTimelimit();
        
        SasParallelPlan spp = null;
        boolean restart = true;
        while (true) {
            timespan++;
            solver.setVariablesCount(translator.setMaxTimespan(timespan));

            try {
                if (restart) {
                    solver.reset();
                    translator.addInitialStateConstraints(solver);
                    for (int i = 0; i < timespan; i++) {
                        translator.addUniversalStateConstraints(solver, i);
                        if (i > 0) {
                            translator.addTransitionConstraints(solver, i - 1);
                        }
                    }
                    restart = false;
                } else {
                    translator.unsetLastGoalStateConstraints(solver);
                }
                translator.setGoalStateConstraints(solver, timespan);
                translator.addUniversalStateConstraints(solver, timespan);
                translator.addTransitionConstraints(solver, timespan - 1);
            } catch (SatContradictionException e) {
                // unsatisfiable for this time
                if (settings.isVerbose()) {
                    System.out.println(String.format("Sat for plan length %4d - UNSAT by Unit Propagation", timespan));
                }
                restart = true;
                continue;
            }

            if (timelimit > 0) {
                int limit = stopwatch.remainingTime(timelimit);
                if (limit <= 0) {
                    throw new TimeoutException();
                }
                solver.setTimeout(limit);
            }
            satisfiable = problemSatisfiable(timespan);
            if (satisfiable && spp == null) {
                if (settings.isVerbose()) {
                    System.out.println("Plan found");
                }
                spp = extractPlan(timespan + 1, solver.getModel());
            }
            if (satisfiable && settings.isDontStop() == false) {
                break;
            }
        }
        spp.setSolverTime(totalSolveTime);
        return spp;
    }

    /**
     * Check if a timeout occurred during plan searching
     * 
     * @return
     */
    public boolean timeoutOccurred() {
        return timeoutOccurred;
    }

    /**
     * Solve planning problem by trying length in a decreasing order return a
     * sub-optimal plan if no optimal plan found
     * 
     * @param startTime decrease from this time
     * @param oneTry try only one step of optimization
     * @return a parallel plan or null if not found
     */
    public SasParallelPlan downwardSolve(int startTime, boolean oneTry) {
        timeoutOccurred = false;
        totalSolveTime.reset();
        int timespan = startTime;
        int timelimit = settings.getTimelimit();

        solver.setVariablesCount(translator.setMaxTimespan(timespan));

        int[] model = null;
        int modelTime = 0;
        int time = 0;
        try {
            translator.addInitialStateConstraints(solver);
            for (time = 0; time < startTime; time++) {
                translator.addUniversalStateConstraints(solver, time);
                translator.addTransitionConstraints(solver, time);
            }
            translator.addUniversalStateConstraints(solver, startTime);

            for (time = timespan; time >= 0; time--) {
                if (timelimit > 0) {
                    int limit = stopwatch.remainingTime(timelimit);
                    if (limit <= 0) {
                        return extractPlan(modelTime+1, model);
                    }
                    solver.setTimeout(limit);
                }
                translator.setGoalStateConstraints(solver, time);
                boolean sat = false;
                try {
                    sat = problemSatisfiable(time);
                } catch (TimeoutException e) {
                    timeoutOccurred = true;
                    sat = false;
                }
                if (sat) {
                    //Store the model
                    model = solver.getModel().clone();
                    modelTime = time;
                } else {
                    break;
                }
                if (oneTry) {
                    break;
                }
            }
        } catch (SatContradictionException e) {
        }
        return extractPlan(modelTime+1, model);
    }

    private boolean problemSatisfiable(int timespan) throws TimeoutException {
        solveTime.reset();
        totalSolveTime.unpause();
        Boolean sat = solver.isSatisfiable();
        if (settings.isVerbose()) {
            System.out.println(String.format("Sat for plan length %4d took %s seconds",
                    timespan, solveTime.elapsedFormatedSeconds()));
        }
        totalSolveTime.pause();
        if (sat != null) {
            return sat == true;
        } else {
            return false;
        }
    }

    /**
     * Extract the plan from the SAT solver
     * 
     * @param timespan the length of the plan
     * @return
     */
    private SasParallelPlan extractPlan(int timespan, int[] model) {
        if (model == null) {
            return null;
        }
        SasParallelPlan spp = translator.decodePlan(model, timespan);
        spp.setSolverTime(totalSolveTime);
        return spp;
    }

    @Override
    public String toString() {
        return "Incremental Solver";
    }

    @Override
    public BasicSettings getSettings() {
        return settings;
    }

}
