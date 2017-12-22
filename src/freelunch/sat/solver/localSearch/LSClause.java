package freelunch.sat.solver.localSearch;

import java.util.Arrays;

public class LSClause {
	
	private int[] literals;
	private int satLiterals;
	private int broken = 2;
	public boolean inqueue;
	
	public LSClause(int[] literals) {
		this.literals = literals;
		satLiterals = 0;
	}
	
	public int[] getLiterals() {
		return literals;
	}
	
	public void decreaseBroken() {
		broken = 2 + (broken >> 1);
	}
	
	/**
	 * How many times has this clause been broken
	 * @return
	 */
	public int getBrokenCount() {
		return broken;
	}

	/**
	 * @return the satLiterals
	 */
	public int getSatLiterals() {
		return satLiterals;
	}

	/**
	 * @param satLiterals the satLiterals to set
	 */
	public void setSatLiterals(int satLiterals) {
		if (satLiterals == 0) {
			broken++;
		}
		this.satLiterals = satLiterals;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(literals) + ", " + satLiterals;
	}
	
	@Override
	public int hashCode() {
		return literals.hashCode();
	}

}
