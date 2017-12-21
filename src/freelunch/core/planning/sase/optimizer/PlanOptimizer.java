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
package freelunch.core.planning.sase.optimizer;

import java.io.IOException;
import java.util.Random;

import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerParameters;
import freelunch.core.planning.sase.optimizer.model.PlanOptimizerStatistics;
import freelunch.core.planning.sase.optimizer.model.SubPlanStructure;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolverSettings;
import freelunch.core.utilities.Stopwatch;


public class PlanOptimizer {

    private PlanOptimizerParameters params;
    private SasProblem workProblem;
    private SasParallelPlan plan;
    private SasProblem problem;
    private Stopwatch stopper;
    private PlanOptimizerStatistics stats;
    private int improvements;

    public PlanOptimizerStatistics optimizePlan(SasProblem problem, SasParallelPlan plan, PlanOptimizerParameters params) {
        stopper = new Stopwatch();
        problem.setActionIDs();
        this.params = params;
        this.plan = plan;
        this.problem = problem;
        this.workProblem = new SasProblem(problem);
        improvements = 0;

        stats = new PlanOptimizerStatistics();
        stats.initialPlanActions = plan.getTotalActions();
        stats.initialPlanTimespan = plan.getPlanLength();

        switch (params.getWindowSelectionAlgorithm()) {
        case randomFixedWindow:
            randomFixedWindowSelection();
            break;
        case systematic:
            systematicSelection();
            break;
        case turbo:
            turboSelection();
            break;
        case fullrandom:
            fullRandomSelection();
            break;
        case exponential:
            exponentialSelection();
            break;
        case limitedrandom:
            limitedRandomSelection();
            break;
        case timedwindows:
            timedWindowsSelection();
            break;
        }

        boolean valid = PlanVerifier.verifyPlan(problem, plan);
        if (!valid) {
            throw new RuntimeException("Optimizer produced an invalid plan");
        }

        stats.finalPlanActions = plan.getTotalActions();
        stats.finalPlanTimespan = plan.getPlanLength();
        stats.solutionTime = stopper.elapsedFormatedSeconds();

        return stats;
    }

    private void timedWindowsSelection() {
        int size = 3;
        Stopwatch mytimer = new Stopwatch();
        while (true) {
            mytimer.reset();
            if (systematicShifting(size, plan.getPlanLength(), false)) {
                return;
            }
            if (mytimer.elapsedSeconds() > params.getMinWindowTime()) {
                break;
            }
            size++;
        }
        turboSelection(size);
    }

    private void exponentialSelection() {
        int size = 2;
        while (!systematicShifting(size, size / 2, params.isFixedPointMode())) {
            int nsize = (int) (size * params.getWindowGrowth());
            if (nsize == size) {
                size++;
            } else {
                size = nsize;
            }
        }
    }

    private void turboSelection() {
        turboSelection(5);
    }

    private void turboSelection(int startSize) {
        for (int size = startSize; true; size++) {
            int shift = (1 + size) / 2;
            if (systematicShifting(size, shift, params.isFixedPointMode())) {
                return;
            }
        }
    }

    /**
     * Keep shifting a window through the plan until the end or until it is
     * getting better, depending on the fixedPoint parameter.
     * 
     * @param shift shifting of the window
     * @param windowSize size of the window
     * @param fixedPoint continue optimization until no improvement occurs
     * @return true if optimization should be stopped
     */
    private boolean systematicShifting(int windowSize, int shift, boolean fixedPoint) {
        while (true) {
            int bestPlanSize = plan.getPlanLength();
            int start = 0;
            if (plan.getPlanLength() <= windowSize) {
                // optimize the entire plan with all the remaining time
                if (stopper.limitExceeded(params.getTotalTimeLimit())) {
                    return true;
                }
                optimizeWindow(0, plan.getPlanLength());
                return true;
            }
            while (start + windowSize < plan.getPlanLength()) {
                if (stopper.limitExceeded(params.getTotalTimeLimit())) {
                    return true;
                }
                optimizeWindow(start, start + windowSize);
                start += shift;
            }
            if (start < plan.getPlanLength()) {
                if (stopper.limitExceeded(params.getTotalTimeLimit())) {
                    return true;
                }
                optimizeWindow(start, plan.getPlanLength());
            }
            if (plan.getPlanLength() >= bestPlanSize) {
                // plan did not improve
                break;
            } else if (params.getIntermediatePlanFileName() != null) {
                improvements++;
                String filename = String.format("%s.%d", params.getIntermediatePlanFileName(), improvements);
                try {
                    plan.saveToFileIpcFormat(filename);
                    System.out.println(String.format("Plan of size %d saved to %s", plan.getPlanLength(),
                            filename));
                } catch (IOException e) {
                    System.out.println("Plan cannot be saved");
                }
            }
            if (fixedPoint == false) {
                break;
            }
        }
        return false;
    }

    private void systematicSelection() {
        int shift = params.getWindowShift();
        int windowSize = params.getWindowSize();
        systematicShifting(windowSize, shift, params.isFixedPointMode());
    }

    private void limitedRandomSelection() {
        //FIXME should not be hard-coded
        int limit = 20;
        Random rnd = new Random(1);
        while (true) {
            int i1 = rnd.nextInt(1 + plan.getPlanLength());
            int i2 = rnd.nextInt(1 + plan.getPlanLength());

            int start = Math.min(i1, i2);
            int end = Math.max(i1, i2);

            if (end - start < 3 || end - start > limit) {
                continue;
            }

            boolean optAll = (start == 0 && end == plan.getPlanLength());

            boolean timeout = optimizeWindow(start, end);

            if (!timeout && optAll) {
                // optimal plan found
                break;
            }

            if (stopper.limitExceeded(params.getTotalTimeLimit())) {
                break;
            }
        }
    }

    private void fullRandomSelection() {
        Random rnd = new Random(1);

        while (true) {
            int i1 = rnd.nextInt(1 + plan.getPlanLength());
            int i2 = rnd.nextInt(1 + plan.getPlanLength());

            int start = Math.min(i1, i2);
            int end = Math.max(i1, i2);

            if (end - start < 3) {
                continue;
            }

            boolean optAll = (start == 0 && end == plan.getPlanLength());

            boolean timeout = optimizeWindow(start, end);

            if (!timeout && optAll) {
                // optimal plan found
                break;
            }

            if (stopper.limitExceeded(params.getTotalTimeLimit())) {
                break;
            }
        }
    }

    private void randomFixedWindowSelection() {
        Random rnd = new Random();
        int iterations = params.getIterations();
        int windowSize = params.getWindowSize();
        for (int it = 0; it < iterations; it++) {
            int range = plan.getPlanLength() - windowSize;
            if (range <= 0) {
                // optimizing the entire plan
                optimizeWindow(0, plan.getPlanLength());
                break;
            }
            int start = rnd.nextInt(range);
            if (stopper.limitExceeded(params.getTotalTimeLimit())) {
                break;
            }
            optimizeWindow(start, start + windowSize);
        }
    }

    /**
     * Compute the time limit for optimizing a window of the specified size
     * 
     * @param windowSize
     * @return time limit in seconds
     */
    private int computeTimeLimit(int windowSize) {
        int time = stopper.remainingTime(params.getTotalTimeLimit());
        int plansize = plan.getPlanLength();
        if (plansize == windowSize) {
            return time;
        }
        float limit = (float)time / ((float)plansize / (float)windowSize);
        return 1 + (int) limit;
    }

    /**
     * Optimize the given subsection of the plan
     * 
     * @param start start of the subsection
     * @param end end of the subsection
     * @return true if timeout occurred, false otherwise
     */
    private boolean optimizeWindow(int start, int end) {
        if (end - start < 3) {
            // window too small for optimization
            return false;
        }
        SubPlanStructure sps = new SubPlanStructure(plan, problem);
        workProblem.setInitialState(sps.getInitialStateForTime(start));
        workProblem.setGoal(sps.getGoalStateForTime(end));
        stats.optimizations++;
        if ((end - start) > stats.maxWindow) {
            stats.maxWindow = end - start;
        }

        IncrementalSolverSettings settings = new IncrementalSolverSettings();
        IncrementalSolver solver = new IncrementalSolver(workProblem, settings);

        // setting the time limits
        if (params.getSolverTimeLimit() != 0) {
            settings.setTimelimit(params.getSolverTimeLimit());
        } else {
            settings.setTimelimit(computeTimeLimit(end - start));
        }

        if (params.isVerbose()) {
            System.out.println(String.format("Optimizing window %d to %d with time limit %d s", start, end,
                    settings.getTimelimit()));
        }

        SasParallelPlan newSubPlan = null;
        boolean entirePlan = start == 0 && end == plan.getPlanLength();
        boolean oneTry = entirePlan ? false : params.isOneTry();

        newSubPlan = solver.downwardSolve(end - start - 2, oneTry);
        if (solver.timeoutOccurred()) {
            stats.optimizationsTimeouted++;
        }
        if (newSubPlan == null) {
            //System.out.println(String.format("%d %d %d %d %s UNCHANGED", start, end, end - start, plan.getPlanLength(), stopper.elapsedFormatedSeconds()));
            return solver.timeoutOccurred();
        }
        stats.improvements++;
        stats.lastImprovementTime = stopper.elapsedFormatedSeconds();
        stats.lastImprovementWindowSize = end - start;

        plan.replaceSubplan(start, end, newSubPlan);
        if (params.isVerbose()) {
            System.out.println(String.format("Subplan optimized from %d to %d. New plan length is %s",
                    end - start, newSubPlan.getPlanLength(), plan.getPlanLength()));
        }
        //System.out.println(String.format("%d %d %d %d %s IMPROVED", start, end, end - start, plan.getPlanLength(), stopper.elapsedFormatedSeconds()));
        if (solver.timeoutOccurred()) {
            if (params.isVerbose()) {
                System.out.println(String.format("TIMEOUT on subplan optimization, window size %d", end - start));
            }
            //System.out.println(String.format("%d %d %d %d %s TIMEOUT", start, end, end - start, plan.getPlanLength(), stopper.elapsedFormatedSeconds()));
        }
        return solver.timeoutOccurred();
    }
}
