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
package freelunch.core.planning.sase.sasToSat.translator;

/**
 * Settings of the SAS to SAT translation
 *
 * @author Tomas Balyo
 * 4.11.2012
 */
public class SaseTranslatorSettings {

    public enum TransitionProgressionType {
        Forward,
        Backward,
        Both
    }

    private TransitionProgressionType transitionProgressionType = TransitionProgressionType.Forward;
    private boolean useSaseTransMutex = false;
    private boolean useOriginalInitialStateEncoding = false;
    private boolean useOriginalGoalEncoding = false;
    
    /**
     * @param transitionProgressionType the transitionProgressionType to set
     */
    public void setTransitionProgressionType(TransitionProgressionType transitionProgressionType) {
        this.transitionProgressionType = transitionProgressionType;
    }

    /**
     * @return the transitionProgressionType
     */
    public TransitionProgressionType getTransitionProgressionType() {
        return transitionProgressionType;
    }

	/**
	 * @return the useSaseTransMutex
	 */
	public boolean isUseSaseTransMutex() {
		return useSaseTransMutex;
	}

	/**
	 * @param useSaseTransMutex the useSaseTransMutex to set
	 */
	public void setUseSaseTransMutex(boolean useSaseTransMutex) {
		this.useSaseTransMutex = useSaseTransMutex;
	}

    /**
     * @return the useOriginalInitialStateEncoding
     */
    public boolean isUseOriginalInitialStateEncoding() {
        return useOriginalInitialStateEncoding;
    }

    /**
     * @param useOriginalInitialStateEncoding the useOriginalInitialStateEncoding to set
     */
    public void setUseOriginalInitialStateEncoding(
            boolean useOriginalInitialStateEncoding) {
        this.useOriginalInitialStateEncoding = useOriginalInitialStateEncoding;
    }

    /**
     * @return the useOriginalGoalEncoding
     */
    public boolean isUseOriginalGoalEncoding() {
        return useOriginalGoalEncoding;
    }

    /**
     * @param useOriginalGoalEncoding the useOriginalGoalEncoding to set
     */
    public void setUseOriginalGoalEncoding(boolean useOriginalGoalEncoding) {
        this.useOriginalGoalEncoding = useOriginalGoalEncoding;
    }
    

}
