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
package freelunch.core.planning.cmdline;

import freelunch.core.planning.sase.sasToSat.incremental.IncrementalSolverSettings;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslatorSettings.TransitionProgressionType;

/**
 * Class for parsing command line arguments
 * @author Tomas Balyo
 * 11.5.2011
 */
public class ArgumentsParser {

    /**
     * Parse the command line parameters and fill
     * the Settings singleton class
     * @param args
     * @return true if arguments are OK
     */
    public IncrementalSolverSettings parseArguments(String[] args) {
        IncrementalSolverSettings settings = new IncrementalSolverSettings();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("-")) {
                char a = arg.charAt(1);
                switch (a) {
                case 'h':
                    printHelp();
                    break;
                case 'c':
                    settings.setCsvOutput(true);
                    break;
                case 'v':
                    settings.setVerbose(true);
                    break;
                case 'd':
                    settings.setDontStop(true);
                    break;
                case 'p':
                    settings.setPrintPlan(true);
                    break;
                case 'q':
                    settings.setPrintPlan(true);
                    i++;
                    settings.setPlanFile(args[i]);
                    break;
                case 's':
                    settings.getTranslationSettings().setUseSaseTransMutex(true);
                    break;
                case 't':
                    int timeout = Integer.parseInt(args[i+1]);
                    i++;
                    settings.setTimelimit(timeout);
                    break;
                case 'r':
                    String transitionProgression = args[i+1];
                    settings.getTranslationSettings().setTransitionProgressionType(
                            TransitionProgressionType.valueOf(transitionProgression));
                    i++;
                    break;
                }
            } else {
                settings.setProblem(arg);
            }
        }
        boolean argsOk = settings.getProblem() != null;
        if (!argsOk) {
            printHelp();
            return null;
        }
        return settings;
    }

    private void printHelp() {
        String msg =
            "\nSas by SAT solver\n" +
            "USAGE: sasSolver.jar filename.sas [-h][-v][-d][-p][-s][-r direction][-t timeout]\n" +
            "	-h: help, prints this help\n" +
            "	-c: csv output, prints information in the following format: problem;result;plan_lenght;total_actions;" +
            "total_runtime;parsing_time;transition_generation_time;total_SAT_solver_time;problem_variables;problem_actions;problem_transitions\n" +
            "	-v: verbose mode, prints additional information about the process of solving\n" +
            "	-d: dont stop, does not stop solving when the plan is found, continues until time limit is reached or killed\n" +
            "   -p: print the plan, prints the resulting plan\n" +
            "   -q: print the plan into a file in thne IPC format, prints the resulting plan\n" +
            "	-s: use SASE transition mutex definition\n" +
            "	-r: transition progression encoding, must be followed by one of {Forward, Backward, Both}, default is Forward\n" +
            "	-t: timeout, sets the timeout for solving, must be followed by an integer representing the desired timeout in seconds\n";
        System.out.println(msg);
    }

}
