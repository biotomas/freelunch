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
package freelunch.core.utilities;

/**
 * Utility class to measure elapsed time
 * @author Tomas Balyo
 * 23.4.2011
 */
public class Stopwatch {

    private long startTime;
    private long elapsedNanos;
    private boolean paused;

    public Stopwatch() {
        reset();
    }

    /**
     * Resets the stop-watch
     */
    public void reset() {
        startTime = System.nanoTime();
        elapsedNanos = 0;
        paused = false;
    }

    /**
     * Pause the time measurement
     */
    public void pause() {
        if (paused) {
            return;
        }
        elapsedNanos += System.nanoTime() - startTime;
        paused = true;
    }

    /**
     * Continue time measurement
     */
    public void unpause() {
        if (!paused) {
            return;
        }
        startTime = System.nanoTime();
        paused = false;
    }

    public long elapsedNanoSeconds() {
        pause();
        unpause();
        return elapsedNanos;
    }
    
    public float elapsedSecondsFloat() {
        return ((float)elapsedNanoSeconds()) / 1e9f;
    }
    
    public long elapsedMilliseconds() {
        return (int) (elapsedNanoSeconds() / (1000*1000));
    }

    public int elapsedSeconds() {
        return (int) (elapsedNanoSeconds() / (1000*1000*1000));
    }

    public String elapsedFormatedSeconds() {
        double elapsedNano = elapsedNanoSeconds();
        return String.format("%.3f",  elapsedNano / 1e9);
    }

    /**
     * Check if the specified time limit has been exceeded
     * since the start.
     * @param limit time limit in seconds
     * @return true if time limit exceeded
     */
    public boolean limitExceeded(int limit) {
        return (limit != 0) && (elapsedSeconds() >= limit);
    }

    /**
     * Compute how much time is left until we reach
     * the specified time limit.
     * @param limit time limit in seconds
     * @return number of seconds until time limit
     */
    public int remainingTime(int limit) {
        return Math.max(0, limit - elapsedSeconds());
    }

}
