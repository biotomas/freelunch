package freelunch.sat.satLifter;

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
        if (!paused) {
            pause();
            unpause();
        }
        return elapsedNanos;
    }
    
    public float elapsedSecondsFloat() {
        return ((float)elapsedNanoSeconds()) / 1e9f;
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
     * @param limit time limit in nanoseconds
     * @return true if time limit exceeded
     */
    public boolean timeLimitExceeded(long nanoseconds) {
        return (nanoseconds != 0) && (elapsedNanoSeconds() >= nanoseconds);
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
