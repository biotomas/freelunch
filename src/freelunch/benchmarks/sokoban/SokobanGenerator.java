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

import freelunch.benchmarks.PlanningProblemGenerator;

public class SokobanGenerator implements PlanningProblemGenerator<Sokoban> {

    private static final String[] SMALL_TEST_1 = new String[] {
            "#####",
            "# @ #",
            "# $.#",
            "#####",
    };

    private static final String[] SMALL_TEST_2 = new String[] {
            "###################",
            "#####   ###########",
            "#####   ###########",
            "#####   ###########",
            "###      ##########",
            "### # ## ##########",
            "#   # ## #####    #",
            "# $               #",
            "##### ### #@##   .#",
            "#####     #########",
            "###################"
    };

    private static final String[] SMALL_TEST_3 = new String[] {
            "#######",
            "# @   #",
            "# $.# #",
            "# $## #",
            "#    .#",
            "#######",
    };

    private static final String[] CLASSIC_1 = new String[] {
            "###################",
            "#####   ###########",
            "#####$  ###########",
            "#####  $###########",
            "###  $ $ ##########",
            "### # ## ##########",
            "#   # ## #####  ..#",
            "# $  $          ..#",
            "##### ### #@##  ..#",
            "#####     #########",
            "###################"
    };

    private static final String[] CLASSIC_2 = new String[] {
            "    ############  ",
            "    #..  #     ###",
            "    #..  # $  $  #",
            "    #..  #$####  #",
            "    #..    @ ##  #",
            "    #..  # #  $ ##",
            "    ###### ##$ $ #",
            "      # $  $ $ $ #",
            "      #    #     #",
            "      ############",
            "                  "
    };

    private static final String[] SMALL_TEST_2_MULTI = new String[] {
            "###################",
            "#####   ###########",
            "#####   ###########",
            "#####   ###########",
            "###      ##########",
            "### # ## ##########",
            "#   # ## ##### @$.#",
            "#            $@$ .#",
            "##### ### #@##   .#",
            "#####     #########",
            "###################"
    };

    private static final String[] CLASSIC_2_MULTI = new String[] {
            "    ############  ",
            "    #.     @   ###",
            "    #.   # $     #",
            "    #.   #$####  #",
            "    #.      @##  #",
            "    #.   # #  $@##",
            "    #### # ##$   #",
            "      #@$        #",
            "      #    #     #",
            "      ############",
            "                  "
    };

    public static final Sokoban SOKOBAN_SMALL_TEST_1 = Sokoban.parseGame(SMALL_TEST_1);
    public static final Sokoban SOKOBAN_SMALL_TEST_2 = Sokoban.parseGame(SMALL_TEST_2);
    public static final Sokoban SOKOBAN_SMALL_TEST_2_MULTI = Sokoban.parseGame(SMALL_TEST_2_MULTI);
    public static final Sokoban SOKOBAN_SMALL_TEST_3 = Sokoban.parseGame(SMALL_TEST_3);
    public static final Sokoban SOKOBAN_CLASSIC_1 = Sokoban.parseGame(CLASSIC_1);
    public static final Sokoban SOKOBAN_CLASSIC_2 = Sokoban.parseGame(CLASSIC_2);
    public static final Sokoban SOKOBAN_CLASSIC_2_MULTI = Sokoban.parseGame(CLASSIC_2_MULTI);

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public Sokoban getNext() {
        return null;
    }

    @Override
    public Sokoban[] getProblems() {
        return new Sokoban[] {
                SokobanGenerator.SOKOBAN_SMALL_TEST_1,
                SokobanGenerator.SOKOBAN_SMALL_TEST_2,
                SokobanGenerator.SOKOBAN_SMALL_TEST_3,
                SokobanGenerator.SOKOBAN_CLASSIC_1,
                SokobanGenerator.SOKOBAN_CLASSIC_2
        };
    }

    @Override
    public String[] getNames() {
        return new String[] {
                "Small test 1",
                "Small test 2",
                "Small test 3",
                "Classic 1",
                "Classic 2"
        };
    }

}
