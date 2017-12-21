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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import freelunch.benchmarks.PlanningProblem;
import freelunch.core.planning.model.ActionInfo;


public class Sokoban implements PlanningProblem {
    public enum Tile {
        SOKOBAN, FLOOR, WALL, PACKAGE, GOAL, PACKAGE_ON_GOAL, SOKOBAN_ON_GOAL;
    }

    public Tile[][] game;

    public Point[] packages;
    public Point[] goals;
    public Point[] floorTiles;
    public Point[] players;

    public static Sokoban parseGame(String[] game) {
        Sokoban sokoban = new Sokoban();
        sokoban.game = new Tile[game.length][game[0].length()];

        List<Point> packages = new ArrayList<Point>();
        List<Point> goals = new ArrayList<Point>();
        List<Point> floorTiles = new ArrayList<Point>();
        List<Point> sokobans = new ArrayList<Point>();

        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[0].length(); j++) {
                switch (game[i].charAt(j)) {
                case '#':
                    sokoban.game[i][j] = Tile.WALL;
                    break;
                case '$':
                    sokoban.game[i][j] = Tile.PACKAGE;
                    packages.add(new Point(i, j));
                    break;
                case ' ':
                    sokoban.game[i][j] = Tile.FLOOR;
                    floorTiles.add(new Point(i, j));
                    break;
                case '.':
                    sokoban.game[i][j] = Tile.GOAL;
                    goals.add(new Point(i, j));
                    break;
                case '*':
                    sokoban.game[i][j] = Tile.PACKAGE_ON_GOAL;
                    goals.add(new Point(i, j));
                    packages.add(new Point(i, j));
                    break;
                case '@':
                    sokoban.game[i][j] = Tile.SOKOBAN;
                    sokobans.add(new Point(i, j));
                    break;
                case '+':
                    sokoban.game[i][j] = Tile.SOKOBAN_ON_GOAL;
                    goals.add(new Point(i, j));
                    sokobans.add(new Point(i, j));
                    break;
                }
            }
        }
        sokoban.floorTiles = floorTiles.toArray(new Point[] {});
        sokoban.packages = packages.toArray(new Point[] {});
        sokoban.goals = goals.toArray(new Point[] {});
        sokoban.players = sokobans.toArray(new Point[] {});
        return sokoban;
    }

    @Override
    public void applyActions(List<ActionInfo> actions) {
        // we know there is only one action
        SokobanMove action = (SokobanMove) actions.get(0);

        Point from = action.from;
        Point to = action.to;
        boolean movingPackage = action.movingPackage;

        if (!movingPackage) {
            game[from.x][from.y] = game[from.x][from.y] == Tile.SOKOBAN ? Tile.FLOOR : Tile.GOAL;
            game[to.x][to.y] = game[to.x][to.y] == Tile.FLOOR ? Tile.SOKOBAN : Tile.SOKOBAN_ON_GOAL;
        } else {
            game[from.x][from.y] = game[from.x][from.y] == Tile.SOKOBAN ? Tile.FLOOR : Tile.GOAL;
            game[to.x][to.y] = game[to.x][to.y] == Tile.PACKAGE ? Tile.SOKOBAN : Tile.SOKOBAN_ON_GOAL;
            Point boxPoint = new Point(to.x + (to.x - from.x), to.y + (to.y - from.y));
            game[boxPoint.x][boxPoint.y] = game[boxPoint.x][boxPoint.y] == Tile.GOAL ? Tile.PACKAGE_ON_GOAL : Tile.PACKAGE;
        }
    }

    @Override
    public Sokoban copy() {
        Sokoban copy = new Sokoban();

        copy.game = new Tile[this.game.length][this.game[0].length];
        for (int i = 0; i < copy.game.length; i++) {
            for (int j = 0; j < copy.game[0].length; j++) {
                copy.game[i][j] = this.game[i][j];
            }
        }

        copy.packages = Arrays.copyOf(packages, packages.length);
        copy.goals = Arrays.copyOf(goals, goals.length);
        copy.floorTiles = Arrays.copyOf(floorTiles, floorTiles.length);
        copy.players = Arrays.copyOf(players, players.length);

        return copy;
    }

    private Sokoban() {
    };

    public static class SokobanMove implements ActionInfo {

        public final Point from, to;
        public final String direction;
        public final boolean movingPackage;

        public SokobanMove(Point from, Point to, String direction, boolean movingPackage) {
            this.from = from;
            this.to = to;
            this.direction = direction;
            this.movingPackage = movingPackage;
        }

        @Override
        public String getName() {
            return movingPackage ?
                    String.format("MovePackage-%s:%s->%s", direction, from, to) :
                    String.format("Move-%s:%s->%s", direction, from, to);
        }

    }

    public static class Point {
        public final int x, y;

        public Point(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }
}
