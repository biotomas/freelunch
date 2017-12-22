package freelunch.sat.bce.utilities;

public class Utils {
	
	/**
	 * Test if all the objects are null
	 * @param objects
	 * @return true if all are null
	 */
	@SafeVarargs
	public static <Type> boolean allNull(Type ... objects) {
		return getNotNull(objects) == null;
	}
	
	/**
	 * Get an object that is not null
	 * @param objects
	 * @return object or null if all are null
	 */
	@SafeVarargs
	public static <Type> Type getNotNull(Type ... objects) {
		for (Type o : objects) {
			if (o != null) {
				return o;
			}
		}
		return null;
	}

}
