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
package freelunch.core.satSolving;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

import freelunch.core.planning.TimeoutException;
import freelunch.core.satModelling.modelObjects.BasicSatFormula;
import freelunch.core.utilities.Stopwatch;

public class ExternalSatSolver implements SatSolver {
    
    private static final String solver = "./lingeling";
    
    private long timeout = 0;
    private long satTime = 0;
    private Boolean sat = null;
    private int[] model = null;
    
    @Override
    public Boolean isSatisfiable(BasicSatFormula formula) throws TimeoutException {
        
        String filename = UUID.randomUUID() + ".cnf";
        int variables = formula.getVariables();
        ExecuteWatchdog watchdog = null; 
        File formulaFile = null;
        sat = null;
        
        try {
            formulaFile = new File(filename);
            formula.printDimacsToFile(filename);
            
            CommandLine cmd = new CommandLine(solver);
            // quiet mode
            cmd.addArgument("-q");
            cmd.addArgument(filename);

            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValues(new int[] {0,10,20});
            
            if (timeout != 0) {
                watchdog = new ExecuteWatchdog(timeout*1000);
                executor.setWatchdog(watchdog);
            }

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(ostream);
            executor.setStreamHandler(streamHandler);
            
            Stopwatch watch = new Stopwatch();
            int exit = executor.execute(cmd);
            satTime = watch.elapsedMilliseconds();
            
            if (watchdog != null && watchdog.killedProcess()) {
                throw new TimeoutException();
            }
            if (exit == 10) {
                sat = true;
                model = new int[variables+1];
                String[] lines = ostream.toString().split("\n");
                for (String line : lines) {
                    if (line.startsWith("v")) {
                        String[] smodel = line.split(" ");
                        for (String slit : smodel) {
                            try {
                                int lit = Integer.parseInt(slit);
                                model[Math.abs(lit)] = lit;
                            } catch (NumberFormatException e) {
                                continue;
                            }
                        }
                    }
                }
            }
            if (exit == 20) {
                sat = false;
            }
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Impossible state");
        } catch (ExecuteException e) {
            throw new TimeoutException();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            formulaFile.delete();
        }

        if (watchdog != null && watchdog.killedProcess()) {
            throw new TimeoutException();
        }

        return sat;
    }

    @Override
    public int[] getModel() {
        return model;
    }

    @Override
    public void setTimeout(int seconds) {
        this.timeout = seconds;
    }

    @Override
    public long getSolveTime() {
        return satTime;
    }

}
