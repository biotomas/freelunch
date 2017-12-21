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
package freelunch.tutorials.slidingPuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import freelunch.benchmarks.PlanningProblemFormalizer;
import freelunch.benchmarks.PlanningProblemGenerator;
import freelunch.benchmarks.slidingPuzzle.SlidingPuzzleFormalizer;
import freelunch.benchmarks.slidingPuzzle.SlidingPuzzleGenerator;
import freelunch.benchmarks.slidingPuzzle.SlidingPuzzleProblem;
import freelunch.core.planning.Solver;
import freelunch.core.planning.model.SasProblem;
import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolver;
import freelunch.visualization.simulation.Simulator;



public class SlidingPuzzleSimulator extends Simulator<SlidingPuzzleProblem> {
    
    public static void main(String[] args) {
        SlidingPuzzleSimulator sim = new SlidingPuzzleSimulator();
        sim.run();
    }

    @Override
    public Solver[] getSupportedSolvers(SasProblem sasProblem, SlidingPuzzleProblem planningProblem) {
        return new Solver[] { new IncrementalSolver(sasProblem) };
    }

    @Override
    public String[] getSupportedSolversNames() {
        return new String[] { "Incremental SASE Solver" };
    }

    @Override
    protected void drawSimulation(Graphics g, SlidingPuzzleProblem instance) {
        //background
        g.setColor(Color.BLACK); 
        g.fillRect(0, 0, panelWidth, panelHeight);
        
        int width = instance.getWidth();
        int height = instance.getHeight();
        int[] board = instance.getBoard();
        
        int max = Math.max(width, height);
        int minPanel = Math.min(panelWidth, panelHeight);
        int blockSize = (minPanel / max) - 10;
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, blockSize / 2);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int fy = (blockSize - fm.getHeight());
        
        
        int dx = (panelWidth - blockSize*width) / 2;
        int dy = (panelHeight - blockSize*height) / 2;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int element = board[y*width + x];
                if (element != 0) {
                    g.setColor(Color.gray);
                    int px = dx + x * blockSize;
                    int py = dy + y * blockSize;
                    g.fill3DRect(px, py, blockSize, blockSize, true);
                    g.setColor(Color.black);
                    String number = Integer.toString(element);
                    int fx = (blockSize - fm.stringWidth(number)) / 2;
                    g.drawString(number, px + fx, py + blockSize - fy);
                }
            }
        }
    }

    @Override
    protected PlanningProblemGenerator<SlidingPuzzleProblem> getProblemGenerator() {
        return new SlidingPuzzleGenerator();
    }

    @Override
    protected PlanningProblemFormalizer<SlidingPuzzleProblem> getProblemFormalizer() {
        return new SlidingPuzzleFormalizer();
    }

}
