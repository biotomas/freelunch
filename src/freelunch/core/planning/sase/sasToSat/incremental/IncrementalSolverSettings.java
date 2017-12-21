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
package freelunch.core.planning.sase.sasToSat.incremental;

import freelunch.core.planning.model.BasicSettings;
import freelunch.core.planning.sase.sasToSat.translator.SaseTranslatorSettings;

/**
 * Settings for the incremental search solver
 * 
 * @author Tomas Balyo 4.11.2012
 */
public class IncrementalSolverSettings extends BasicSettings {

    public IncrementalSolverSettings() {
        super();
    }

    public IncrementalSolverSettings(BasicSettings settings) {
        super(settings);
    }

    // settings with their default values
    private boolean dontStop = false;
    private boolean printPlan = false;
    private boolean csvOutput = false;
    private String problem = null;
    private SaseTranslatorSettings translationSettings = new SaseTranslatorSettings();
    private String planFile = null;

    /**
     * @return the dontStop
     */
    public boolean isDontStop() {
        return dontStop;
    }

    /**
     * @param dontStop the dontStop to set
     */
    public void setDontStop(boolean dontStop) {
        this.dontStop = dontStop;
    }

    /**
     * @return the printPlan
     */
    public boolean isPrintPlan() {
        return printPlan;
    }

    /**
     * @param printPlan the printPlan to set
     */
    public void setPrintPlan(boolean printPlan) {
        this.printPlan = printPlan;
    }

    /**
     * @param problem the problem to set
     */
    public void setProblem(String problem) {
        this.problem = problem;
    }

    /**
     * @return the problem
     */
    public String getProblem() {
        return problem;
    }

    /**
     * @param csvOutput the csvOutput to set
     */
    public void setCsvOutput(boolean csvOutput) {
        this.csvOutput = csvOutput;
    }

    /**
     * @return the csvOutput
     */
    public boolean isCsvOutput() {
        return csvOutput;
    }

    /**
     * @return the translationSettings
     */
    public SaseTranslatorSettings getTranslationSettings() {
        return translationSettings;
    }

    /**
     * @param translationSettings the translationSettings to set
     */
    public void setTranslationSettings(SaseTranslatorSettings translationSettings) {
        this.translationSettings = translationSettings;
    }

    /**
     * @return the planFile
     */
    public String getPlanFile() {
        return planFile;
    }

    /**
     * @param planFile the planFile to set
     */
    public void setPlanFile(String planFile) {
        this.planFile = planFile;
    }

}
