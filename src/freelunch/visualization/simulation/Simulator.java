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
package freelunch.visualization.simulation;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import freelunch.benchmarks.PlanningProblem;
import freelunch.benchmarks.PlanningProblemFormalizer;
import freelunch.benchmarks.PlanningProblemGenerator;
import freelunch.core.planning.NonexistentPlanException;
import freelunch.core.planning.Solver;
import freelunch.core.planning.TimeoutException;
import freelunch.core.planning.TraceModel;
import freelunch.core.planning.Traceable;
import freelunch.core.planning.model.ActionInfo;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.SasParallelPlan;
import freelunch.core.planning.model.SasProblem;
import freelunch.visualization.debugging.TraceFrame;



public abstract class Simulator<T extends PlanningProblem> {

    private final static int MAKE_BUTTON = 0, START_BUTTON = 1,
            STOP_BUTTON = 2;
    private final static int STATE_CLEAR = 0, STATE_PLAN_READY = 1,
            STATE_RUNNING = 2;

    int state = STATE_CLEAR;

    public static final int PAUSE_NORMAL = 700;
    public static final int PAUSE_FAST = 300;
    public static final int PAUSE_ULTRA = 20;

    private SimulatorFrame<T> frame;
    private SimulationPanel simulationPanel;

    private SasParallelPlan plan;

    protected T currentInstance;

    protected int panelWidth;
    protected int panelHeight;

    private int planIndex = 0;
    private Timer timer;

    private void initFrame() {
        frame = new SimulatorFrame<T>();
        frame.btnMakePlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed(MAKE_BUTTON);
            }
        });

        frame.btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed(START_BUTTON);
            }
        });

        frame.btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed(STOP_BUTTON);
            }
        });
        setButtonsEnabled(true, false, false);

        frame.problemSelector.addPropertyChangeListener(ProblemSelector.PROPERTY_GENERATOR, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                resetPlanningProblem();
            }
        });

        frame.problemSelector.setGenerator(getProblemGenerator());

        frame.comboBox.setModel(new DefaultComboBoxModel<Object>(getSupportedSolversNames()));
        frame.speedComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "Normal", "Fast", "Ultra fast" }));

        resetPlanningProblem();
    }

    private void resetPlanningProblem() {
        this.currentInstance = frame.problemSelector.getPlanningProblem();

        simulationPanel = new SimulationPanel();
        simulationPanel.setProblemInstance(currentInstance);

        frame.leftScrollPane.setViewportView(simulationPanel);
    }

    private void buttonPressed(int a) {
        switch (a) {
        case MAKE_BUTTON:
            if (state == STATE_CLEAR) {

                resetPlanningProblem();

                // Use the tutorial to generate the problem
                PlanningProblemFormalizer<T> formalizer = getProblemFormalizer();
                SasProblem sasProb = formalizer.formalize(frame.problemSelector.getPlanningProblem()).getSasProblem();

                Solver[] supportedSolvers = getSupportedSolvers(sasProb, frame.problemSelector.getPlanningProblem());
                final Solver s = supportedSolvers[frame.comboBox.getSelectedIndex()];

                // Start tracing if enabled
                if (s.getSettings().isTracingEnabled() && s instanceof Traceable) {
                    final TraceModel traceModel = ((Traceable) s).getTraceModel();
                    final TraceFrame traceFrame = new TraceFrame(traceModel, true);

                    traceModel.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            traceFrame.update(traceModel);
                        }
                    });
                    traceFrame.setVisible(true);
                }

                setButtonsEnabled(false, false, false);

                SwingWorker<SasParallelPlan, Void> sw = new SwingWorker<SasParallelPlan, Void>() {
                    @Override
                    protected SasParallelPlan doInBackground() throws Exception {
                        return s.solve();
                    }

                    @Override
                    protected void done() {
                        try {
                            plan = get();
                        } catch (InterruptedException e) {
                        } catch (ExecutionException e) {
                            if (e.getCause() instanceof NonexistentPlanException) {
                                log("No plan could be created \n");
                            } else if (e.getCause() instanceof TimeoutException) {
                                log("Time limit exceeded \n");
                            }
                        }
                        if (plan != null) {
                            state = STATE_PLAN_READY;
                            setButtonsEnabled(true, true, false);
                            planIndex = 0;
                            log("Plan created \n");
                            log("Plan size: " + plan.getTotalActions() + "\n");
                        } else {
                            setButtonsEnabled(true, false, false);
                        }
                    }
                };
                sw.execute();
            }
            break;
        case START_BUTTON:
            if (state == STATE_PLAN_READY) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scheduleTimer();
                    }
                });
                state = STATE_RUNNING;
                setButtonsEnabled(false, false, true);
                log("Simulation started \n");
            }
            break;
        case STOP_BUTTON:
            if (state == STATE_RUNNING) {
                state = STATE_CLEAR;
                timer.cancel();
                timer = null;
                setButtonsEnabled(true, false, false);
                log("Simulation stopped \n");
            }
            break;
        }
    }

    private void setButtonsEnabled(boolean plan, boolean start, boolean stop) {
        frame.btnMakePlan.setEnabled(plan);
        frame.btnStart.setEnabled(start);
        frame.btnStop.setEnabled(stop);
        // These fields have sense only when the "start" or "make plan" buttons have.
        frame.speedComboBox.setEnabled(plan);
        frame.comboBox.setEnabled(plan);
    }

    /**
     * Provide the supported solvers given a problem.
     * 
     * @param sasProblem the formalized sas problem.
     * @param planningProblem the instance of the original problem.
     */
    public abstract Solver[] getSupportedSolvers(SasProblem sasProblem, T planningProblem);

    /**
     * Provide the display names of the supported solvers.
     */
    public abstract String[] getSupportedSolversNames();

    /**
     * Implement drawing of one state of a simulation process onto a given
     * {@link Graphics} object.
     * 
     * @param g {@link Graphics} object to draw on.
     * @param visualizedProblem instance of a visualized problem.
     */
    protected abstract void drawSimulation(Graphics g, T instance);

    /**
     * Implement to provide instances representing the simulated problem.
     */
    protected abstract PlanningProblemGenerator<T> getProblemGenerator();

    /**
     * Implement to provide the formalized version of a given problem.
     */
    protected abstract PlanningProblemFormalizer<T> getProblemFormalizer();

    /**
     * Get current basic settings from the settings panel.
     */
    protected final BasicSettings getSettings() {
        return frame.basicSettingsPanel.getSettigs();
    }

    protected final void log(String message) {
        frame.textArea.insert(message, 0);
    }

    private void scheduleTimer() {
        final List<List<SasAction>> p = plan.getPlan();

        timer = new Timer();
        final Timer t = timer;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (planIndex >= p.size()) {
                    t.cancel();
                    buttonPressed(STOP_BUTTON);
                    return;
                }
                List<SasAction> ops = p.get(planIndex);
                List<ActionInfo> actions = new ArrayList<ActionInfo>();
                for (SasAction sasAction : ops) {
                    actions.add(sasAction.getActionInfo());
                }
                currentInstance.applyActions(actions);

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        simulationPanel.repaint();
                    }
                });
                log("Frame " + planIndex + " \n");
                planIndex++;
            }
        }, 0, getPause());
    }

    private int getPause() {
        if (frame.speedComboBox.getSelectedItem().equals("Normal")) {
            return PAUSE_NORMAL;
        } else if (frame.speedComboBox.getSelectedItem().equals("Fast")) {
            return PAUSE_FAST;
        } else if (frame.speedComboBox.getSelectedItem().equals("Ultra fast")) {
            return PAUSE_ULTRA;
        }
        return -1;
    }

    public void run() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
                frame.setVisible(true);
            }
        });
    }

    class SimulationPanel extends JPanel {
        private static final long serialVersionUID = 2933190557624504739L;

        public SimulationPanel() {
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Simulator.this.panelWidth = getWidth();
                    Simulator.this.panelHeight = getHeight();
                }
            });
        }

        T problemInstance;

        void setProblemInstance(T instance) {
            this.problemInstance = instance;
        }

        @Override
        public void paint(Graphics g) {
            if (problemInstance == null) {
                return;
            }
            Simulator.this.panelWidth = getWidth();
            Simulator.this.panelHeight = getHeight();
            drawSimulation(g, problemInstance);
        }

    }

}
