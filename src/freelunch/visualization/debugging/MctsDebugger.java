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
package freelunch.visualization.debugging;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import freelunch.benchmarks.sokoban.Sokoban;
import freelunch.benchmarks.sokoban.SokobanFormalizer;
import freelunch.benchmarks.sokoban.SokobanGenerator;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.montecarlo.MctsSettings;
import freelunch.core.planning.montecarlo.MctsSolver;
import freelunch.core.planning.montecarlo.MctsTraceModel;


public class MctsDebugger {

    private final MctsSolver solver;
    private SasParallelPlan plan;

    public static void main(String[] args) {

        Sokoban sokoban = SokobanGenerator.SOKOBAN_CLASSIC_1;
        SokobanFormalizer sokobanFormalizer = new SokobanFormalizer();
        MctsSolver solver = new MctsSolver(sokobanFormalizer.formalize(sokoban).getSasProblem(), new MctsSettings(), 1);

        //MctsSolver solver = new MctsSolver(formalizer.formalize(logistics).getSasProblem(), new MctsSettings(), 1);
        ((MctsSettings) solver.getSettings()).setTracingEnabled(false);
        ((MctsSettings) solver.getSettings()).setNumIterations(100);
        ((MctsSettings) solver.getSettings()).setRolloutLength(0);
        ((MctsSettings) solver.getSettings()).setNumRollouts(1);
        MctsDebugger debugger = new MctsDebugger(solver);
        debugger.startDebug(false);
    }

    public MctsDebugger(MctsSolver solver) {
        this.solver = solver;
    }

    public void startDebug(final boolean updateDefault) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MctsTraceModel traceModel = (MctsTraceModel) solver.getTraceModel();
                final TraceFrame traceFrame = new TraceFrame(traceModel, updateDefault);

                traceModel.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        traceFrame.update(traceModel);
                    }
                });
                traceFrame.setVisible(true);
            }
        });

        SwingWorker<SasParallelPlan, Void> sw = new SwingWorker<SasParallelPlan, Void>() {
            @Override
            protected SasParallelPlan doInBackground() throws Exception {
                return solver.solve();
            }

            @Override
            protected void done() {
                try {
                    plan = get();
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof NonexistentPlanException) {
                        System.out.println("No plan could be created \n");
                    } else if (e.getCause() instanceof TimeoutException) {
                        System.out.println("Time limit exceeded \n");
                    }
                }
                if (plan != null) {
                    System.out.println("Plan created \n");
                    System.out.println("Plan size: " + plan.getTotalActions() + "\n");
                }
            }
        };
        sw.execute();
    }

}
