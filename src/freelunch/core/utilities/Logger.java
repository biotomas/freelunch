package freelunch.core.utilities;

/**
 * A simple singleton logger
 */
public enum Logger {
	INSTANCE;

	private int verbosity = 0;
	private final Stopwatch watch = new Stopwatch();
	
	public static void resetWatch() {
		INSTANCE.watch.reset();
	}
	
	public static void setVerbosity(int verbosity) {
		INSTANCE.verbosity = verbosity;
	}
	
	public static void print(int level, String message) {
		if (level <= INSTANCE.verbosity) {
			System.out.println(String.format("%s [%s]", message, INSTANCE.watch.elapsedFormatedSeconds()));
		}
	}

}
