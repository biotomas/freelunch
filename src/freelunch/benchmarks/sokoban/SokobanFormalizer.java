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
package freelunch.benchmarks.sokoban;

import java.awt.geom.Point2D;

import freelunch.benchmarks.PlanningProblemFormalizer;
import freelunch.benchmarks.sokoban.Sokoban.Point;
import freelunch.benchmarks.sokoban.Sokoban.SokobanMove;
import freelunch.benchmarks.sokoban.Sokoban.Tile;
import freelunch.core.planning.model.Condition;
import freelunch.core.planning.model.SasAction;
import freelunch.core.planning.model.StateVariable;
import freelunch.core.planning.sase.sasToSat.SasProblemBuilder;


public class SokobanFormalizer implements PlanningProblemFormalizer<Sokoban> {

    final static int DOM_FLOOR = 0;
    final static int DOM_WALL = 1;

    final static int DOM_NO = 0;
    final static int DOM_YES = 1;

    Sokoban sokoban;
    int height;
    int width;
    int boardSize;

    @Override
    public SasProblemBuilder formalize(Sokoban sokoban) {
        this.sokoban = sokoban;
        this.height = sokoban.game.length;
        this.width = sokoban.game[0].length;
        this.boardSize = height * width;

        // Initialize the planning problem
        SasProblemBuilder sasProblemBuilder = new SasProblemBuilder();

        // ==========================
        // Create the state variables
        // ==========================

        // State variable of sokoban location
        StateVariable playerLocation = sasProblemBuilder.newVariable("sokoban", boardSize);

        // State variables of tiles
        StateVariable tiles[] = new StateVariable[boardSize];
        for (int i = 0; i < boardSize; i++) {
            tiles[i] = sasProblemBuilder.newVariable("tile" + i, 2);
        }

        // State variables defining whether a tile contains a package
        StateVariable tileHasPackage[] = new StateVariable[boardSize];
        for (int i = 0; i < boardSize; i++) {
            tileHasPackage[i] = sasProblemBuilder.newVariable("hasPackage" + i, 2);
        }

        // ==============================
        // Define the actions (operators)
        // ==============================

        for (int i = 0; i < boardSize; i++) {
            addMoveAction(sasProblemBuilder, playerLocation, tileHasPackage, i);
            addMovePackageAction(sasProblemBuilder, playerLocation, tiles, tileHasPackage, i);
        }

        // ================================
        // Define the initial state
        // ================================

        // player location
        sasProblemBuilder.addInitialStateCondition(new Condition(playerLocation, toLinear(sokoban.players[0])));

        // packages and goals locations
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                switch (sokoban.game[i][j]) {
                case FLOOR:
                case GOAL:
                case PACKAGE_ON_GOAL:
                case SOKOBAN_ON_GOAL:
                    sasProblemBuilder.addInitialStateCondition(new Condition(tiles[toLinear(new Point(i, j))], DOM_FLOOR));
                    break;
                case WALL:
                    sasProblemBuilder.addInitialStateCondition(new Condition(tiles[toLinear(new Point(i, j))], DOM_WALL));
                    break;
                default:
                    break;
                }
                if (sokoban.game[i][j] == Tile.PACKAGE || sokoban.game[i][j] == Tile.PACKAGE_ON_GOAL) {
                    sasProblemBuilder.addInitialStateCondition(new Condition(tileHasPackage[toLinear(new Point(i, j))], DOM_YES));
                } else {
                    sasProblemBuilder.addInitialStateCondition(new Condition(tileHasPackage[toLinear(new Point(i, j))], DOM_NO));
                }
            }
        }

        // =================================
        // Define the goal state
        // =================================

        // we only care about the final locations of packages. All tiles that are goals must contain a package.
        for (Point point : sokoban.goals) {
            sasProblemBuilder.addGoalCondition(new Condition(tileHasPackage[toLinear(point)], DOM_YES));
        }
        for (Point point : sokoban.floorTiles) {
            sasProblemBuilder.addGoalCondition(new Condition(tileHasPackage[toLinear(point)], DOM_NO));
        }

        return sasProblemBuilder;
    }

    // Create action when the player is on the location (x, y) and moves in any possible direction.
    private void addMoveAction(SasProblemBuilder problem, StateVariable playerLocation, StateVariable[] tileHasPackage, int from) {
        if (sokoban.game[toPoint(from).x][toPoint(from).y] == Tile.WALL) {
            return;
        }
        for (int direction = 0; direction < 4; direction++) {
            int to = step(direction, from);
            if (to != -1 && sokoban.game[toPoint(to).x][toPoint(to).y] != Tile.WALL) {
                SasAction op = problem.newAction(new SokobanMove(toPoint(from), toPoint(to), directions[direction], false));

                op.getPreconditions().add(new Condition(playerLocation, from));
                op.getPreconditions().add(new Condition(tileHasPackage[to], DOM_NO));

                op.getEffects().add(new Condition(playerLocation, to));
            }
        }
    }

    private void addMovePackageAction(SasProblemBuilder problem, StateVariable playerLocation, StateVariable[] tiles, StateVariable[] tileHasPackage, int from) {
        if (sokoban.game[toPoint(from).x][toPoint(from).y] == Tile.WALL) {
            return;
        }
        for (int direction = 0; direction < 4; direction++) {
            // player is on the "from" tile
            // box is on the "box" tile
            // box is moved onto the "to" tile
            int box = step(direction, from);
            int to = step(direction, box);
            if (to != -1 && box != -1 && sokoban.game[toPoint(box).x][toPoint(box).y] != Tile.WALL && sokoban.game[toPoint(to).x][toPoint(to).y] != Tile.WALL) {
                SasAction op = problem.newAction(new SokobanMove(toPoint(from), toPoint(box), directions[direction], true));

                op.getPreconditions().add(new Condition(playerLocation, from));
                op.getPreconditions().add(new Condition(tileHasPackage[box], DOM_YES));
                op.getPreconditions().add(new Condition(tileHasPackage[to], DOM_NO));

                op.getEffects().add(new Condition(playerLocation, box));
                op.getEffects().add(new Condition(tileHasPackage[box], DOM_NO));
                op.getEffects().add(new Condition(tileHasPackage[to], DOM_YES));
            }
        }
    }

    /**
     * Function used for simple euclidian distance heuristic.
     * 
     * @param sokoban
     * @return the sum of all euclidian distances to the goals.
     */
    public static double computeDistancesFromGoals(int[] variables, Sokoban originalProblem) {
        int height = originalProblem.game.length;
        int width = originalProblem.game[0].length;

        Point[] boxLocations = new Point[originalProblem.packages.length];
        int j = 0;
        // For cycle through "tileHasPackage" variables.
        for (int i = height * width + 1; i < variables.length; i++) {
            if (variables[i] == DOM_YES) {
                boxLocations[j] = toPoint(i - (height * width + 1), width, height);
                j++;
            }
        }
        boolean[] includedBoxes = new boolean[originalProblem.goals.length];
        double totalDistance = 0;
        for (int i = 0; i < originalProblem.goals.length; i++) {
            int closestIndex = -1;
            double closestValue = Double.MAX_VALUE;

            for (j = 0; j < includedBoxes.length; j++) {
                if (!includedBoxes[j]) {
                    double dist = Point2D.distance(originalProblem.goals[i].x, originalProblem.goals[i].y, boxLocations[j].x, boxLocations[j].y);
                    if (dist < closestValue) {
                        closestValue = dist;
                        closestIndex = j;
                    }
                }
            }
            includedBoxes[closestIndex] = true;
            totalDistance += closestValue;
            System.out.print(closestValue + "  ");
        }
        System.out.println(totalDistance);
        return totalDistance;
    }

    /**
     * @return linearized version of a 2D coordinate.
     */
    private int toLinear(Point point) {
        return point.x * width + point.y;
    }

    /**
     * @return 2D version of a linearized coordinate.
     */
    private static Point toPoint(int a, int width, int height) {
        return new Point(a / width, a % width);
    }

    /**
     * @return 2D version of a linearized coordinate.
     */
    private Point toPoint(int a) {
        return toPoint(a, width, height);
    }

    /**
     * Creates a new linearized coordinate in a given direction.
     * 
     * @return new coordinate after applying one step, or -1 if step is not
     * valid.
     */
    private int step(int direction, int from) {
        Point point = toPoint(from);
        Point newPoint = new Point(point.x + steps[direction][0], point.y + steps[direction][1]);
        return (newPoint.x >= 0 && newPoint.x < height && newPoint.y >= 0 && newPoint.y < width) ? toLinear(newPoint) : -1;
    }

    private final static int[][] steps = new int[][] { new int[] { 1, 0 }, new int[] { -1, 0 }, new int[] { 0, 1 }, new int[] { 0, -1 } };
    private final static String[] directions = new String[] { "DOWN", "UP", "RIGHT", "LEFT" };

}
