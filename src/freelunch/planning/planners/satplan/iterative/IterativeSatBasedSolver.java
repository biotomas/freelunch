/*******************************************************************************
 * Copyright (c) 2013 Tomas Balyo and Vojtech Bardiovsky
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
package freelunch.planning.planners.satplan.iterative;

import freelunch.planning.model.BasicSettings;
import freelunch.planning.model.NonexistentPlanException;
import freelunch.planning.model.SasParallelPlan;
import freelunch.planning.model.TimeoutException;
import freelunch.planning.planners.Planner;
import freelunch.planning.planners.satplan.translator.SasToSatTranslator;
import freelunch.planning.planners.satplan.translator.TransitionExistStepTranslator;
import freelunch.sat.model.CnfSatFormula;
import freelunch.sat.model.FormulaAnalyzer;
import freelunch.sat.model.SatSolver;
import freelunch.utilities.Stopwatch;

public class IterativeSatBasedSolver implements Planner {
    
    public static class Stats {
        public int satTime;
        public String totalTime;
    }
    
    private SatSolver solver;
    private SasToSatTranslator translator;
    private BasicSettings settings = new BasicSettings();
    private int makespan;
    private Stats stats;
    
    public IterativeSatBasedSolver(SatSolver solver, SasToSatTranslator translator) {
        this.solver = solver;
        this.translator = translator;
        stats = new Stats();
    }
    
    public Stats getStats() {
        return stats;
    }
    
    public int getLastMakepan() {
        return makespan;
    }

    @Override
    public SasParallelPlan solve() throws TimeoutException, NonexistentPlanException {
        Stopwatch watch = new Stopwatch();
        int satTime = 0;
        int timeLimit = settings.getTimelimit();
        int satLimit = settings.getSatLimit();
        makespan = 0;
        
        while (true) {
            makespan++;
            CnfSatFormula formula = translator.makeFormulaForMakespan(makespan);
            if (formula == null) {
                continue;
            }
            
            if (settings.isVerbose()) {
                System.out.println("Formula for makespan = " + makespan);
                FormulaAnalyzer.analyzeFormula(formula).print();
            }
            
            if (timeLimit > 0 || satLimit > 0) {
                int remTime = Integer.MAX_VALUE;
                if (timeLimit > 0) {
                    remTime = watch.remainingTime(timeLimit);
                }
                int remSatTime = Integer.MAX_VALUE;
                if (satLimit > 0) {
                    remSatTime = (int) (satLimit -  satTime);
                }
                if (remTime <= 0 || remSatTime <= 0) {
                    stats.satTime = satTime;
                    stats.totalTime = watch.elapsedFormatedSeconds();
                    throw new TimeoutException();
                }
                solver.setTimeout(Math.min(remTime, remSatTime));
            }
            
            if (solver.isSatisfiable(formula)) {
                if (settings.isVerbose() && translator instanceof TransitionExistStepTranslator) {
                    System.out.println("Transition Plan:");
                    ((TransitionExistStepTranslator)translator).printTransitionPlan(solver.getModel(), makespan);
                }
                satTime += solver.getSolveTime();
                stats.satTime = satTime;
                stats.totalTime = watch.elapsedFormatedSeconds();
                return translator.decodePlan(solver.getModel(), makespan);
            }
            satTime += solver.getSolveTime();
            if (settings.isVerbose()) {
                System.out.println("Solved for makespan " + makespan);
            }
        }
    }
    
    @Override
    public BasicSettings getSettings() {
        return settings;
    }

}
