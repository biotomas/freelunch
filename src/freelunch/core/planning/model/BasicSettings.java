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
package freelunch.core.planning.model;

/**
 * This class holds the basic settings of planners. Planners should extend this
 * class to add additional settings
 * 
 * @author Tomas Balyo 4.11.2012
 */
public class BasicSettings {

    // properties with default values
    private int timelimit = 0;
    private int satLimit = 0;
    private boolean verbose = false;
    private boolean tracingEnabled = true;

    public BasicSettings() {
    }

    public BasicSettings(BasicSettings settings) {
        this.timelimit = settings.timelimit;
        this.verbose = settings.verbose;
    }

    public BasicSettings(int timelimit, boolean verbose) {
        super();
        this.timelimit = timelimit;
        this.verbose = verbose;
    }

    /**
     * Print additional information for debug purposes
     * 
     * @return the verbose
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * Print additional information for debug purposes
     * 
     * @param verbose the verbose to set
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Time limit for planning, 0 means no limit
     * 
     * @return the timelimit
     */
    public int getTimelimit() {
        return timelimit;
    }

    /**
     * Time limit for planning in seconds, 0 means no limit
     * 
     * @param timelimit the timelimit to set
     */
    public void setTimelimit(int timelimit) {
        this.timelimit = timelimit;
    }

    /**
     * Is tracing of the solver algorithm enabled.
     * 
     * @return is tracing enabled
     */
    public boolean isTracingEnabled() {
        return tracingEnabled;
    }

    /**
     * Set tracing of the solver algorithm.
     * 
     * @param tracingEnabled is tracing enabled
     */
    public void setTracingEnabled(boolean tracingEnabled) {
        this.tracingEnabled = tracingEnabled;
    }

    /**
     * The time limit for the SAT solver
     * @return the satLimit
     */
    public int getSatLimit() {
        return satLimit;
    }

    /**
     * The time limit for the SAT solver
     * @param satLimit the satLimit to set
     */
    public void setSatLimit(int satLimit) {
        this.satLimit = satLimit;
    }

}
