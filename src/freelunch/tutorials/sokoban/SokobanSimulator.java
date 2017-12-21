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
package freelunch.tutorials.sokoban;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.imageio.ImageIO;

import freelunch.benchmarks.PlanningProblemFormalizer;
import freelunch.benchmarks.PlanningProblemGenerator;
import freelunch.benchmarks.sokoban.Sokoban;
import freelunch.benchmarks.sokoban.SokobanFormalizer;
import freelunch.benchmarks.sokoban.SokobanGenerator;
import freelunch.benchmarks.sokoban.Sokoban.Tile;
import freelunch.core.planning.Solver;
import freelunch.core.planning.forwardSearch.BasicForwardSearchSolver;
import freelunch.core.planning.forwardSearch.ForwardSearchSettings;
import freelunch.core.planning.forwardSearch.heuristics.StateVariablesValueGoalDistanceHeuristic;
import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.montecarlo.MctsSettings;
import freelunch.core.planning.montecarlo.MctsSolver;
import freelunch.core.planning.montecarlo.NormalizedHeuristicFunction;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolverSettings;
import freelunch.visualization.simulation.Simulator;



public class SokobanSimulator extends Simulator<Sokoban> {

    EnumMap<Sokoban.Tile, Image> imageMap = new EnumMap<Tile, Image>(Tile.class);

    int LEFT = 40;
    int TOP = 40;
    int TILE_SIZE = 32;

    public static void main(String[] args) {
        // Initialize the simulator itself
        SokobanSimulator sokobanSimulator = new SokobanSimulator();
        sokobanSimulator.run();
    }

    public SokobanSimulator() {
        try {
            for (Tile tile : Tile.values()) {
                String name = "";
                switch (tile) {
                case FLOOR:
                    name = "floor";
                    break;
                case WALL:
                    name = "wall";
                    break;
                case GOAL:
                    name = "dock";
                    break;
                case PACKAGE:
                    name = "box-fl";
                    break;
                case PACKAGE_ON_GOAL:
                    name = "box-docked-fl";
                    break;
                case SOKOBAN:
                    name = "worker";
                    break;
                case SOKOBAN_ON_GOAL:
                    name = "worker-docked";
                    break;
                }
                String filename = "resources/borgar-sokoban/yoshi-32/yoshi-32-" + name + ".png";
                Image image = ImageIO.read(new File(filename));
                imageMap.put(tile, image);
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    @Override
    protected PlanningProblemGenerator<Sokoban> getProblemGenerator() {
        return new SokobanGenerator();
    }

    @Override
    protected PlanningProblemFormalizer<Sokoban> getProblemFormalizer() {
        return new SokobanFormalizer();
    }

    @Override
    public String[] getSupportedSolversNames() {
        return new String[] { "Incremental", "Basic Forward Search", "MCTS" };
    }

    @Override
    public Solver[] getSupportedSolvers(SasProblem sasProb, Sokoban sokoban) {
        BasicSettings settings = getSettings();

        List<Solver> list = new ArrayList<Solver>();
        list.add(new IncrementalSolver(sasProb, new IncrementalSolverSettings(settings)));

        ForwardSearchSettings forwardSearchSettings = new ForwardSearchSettings(settings);
        forwardSearchSettings.setHeuristic(new StateVariablesValueGoalDistanceHeuristic(sasProb, 1));
        list.add(new BasicForwardSearchSolver(sasProb, forwardSearchSettings));

        MctsSettings mctsSettings = new MctsSettings(settings);
        mctsSettings.setHeuristic(new SokobanHeuristic(sokoban));
        list.add(new MctsSolver(sasProb, mctsSettings));

        return list.toArray(new Solver[] {});
    }

    @Override
    protected void drawSimulation(Graphics g, Sokoban instance) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelWidth, panelHeight);
        Sokoban sokoban = instance;
        for (int i = 0; i < sokoban.game.length; i++) {
            for (int j = 0; j < sokoban.game[0].length; j++) {
                g.drawImage(imageMap.get(sokoban.game[i][j]), LEFT + j * TILE_SIZE, TOP + i * TILE_SIZE, null);
            }
        }
    }

    class SokobanHeuristic implements NormalizedHeuristicFunction {
        final Sokoban sokoban;

        public SokobanHeuristic(Sokoban sokoban) {
            this.sokoban = sokoban;
        }

        @Override
        public double evaluate(int[] state) {
            return 1 - 1 / (1 + Math.exp(-SokobanFormalizer.computeDistancesFromGoals(state, sokoban)));
        }
    }

}
