package freelunch.sat.solver.localSearch.selectors;

import java.util.Arrays;
import java.util.Random;

import freelunch.sat.solver.localSearch.SimpleClauseManager;
import freelunch.sat.solver.localSearch.BaseWalkSAT.LocalSearchMetadata;


public class Sparrow implements LocalSearchSelector {

    public static class SparrowSettings {
        private double scoreBaseExp = 4; // c1
        private double ageExponent = 4;  // c2
        private double ageDivisor = 1e5; // c3
        /**
         * @return the scoreBaseExp
         */
        public double getScoreBaseExp() {
            return scoreBaseExp;
        }
        /**
         * @param scoreBaseExp the scoreBaseExp to set
         */
        public void setScoreBaseExp(double scoreBaseExp) {
            this.scoreBaseExp = scoreBaseExp;
        }
        /**
         * @return the ageExponent
         */
        public double getAgeExponent() {
            return ageExponent;
        }
        /**
         * @param ageExponent the ageExponent to set
         */
        public void setAgeExponent(double ageExponent) {
            this.ageExponent = ageExponent;
        }
        /**
         * @return the ageDivisor
         */
        public double getAgeDivisor() {
            return ageDivisor;
        }
        /**
         * @param ageDivisor the ageDivisor to set
         */
        public void setAgeDivisor(double ageDivisor) {
            this.ageDivisor = ageDivisor;
        }
    }
    
	
	private int time = 0;
	private int[] ages = null;
	private SparrowSettings settings;
	private SimpleClauseManager clManager;
	private Random random;

	@Override
	public void prepareForFlipping(LocalSearchMetadata lsData) {
		this.random = lsData.random;
		this.clManager = lsData.clManager;
		int variables = clManager.getVariableCount();
        if (ages == null || ages.length != variables) {
            ages = new int[variables+1];
        }
        Arrays.fill(ages, 0);
        time = 0;
	}

	public Sparrow(SparrowSettings settings) {
	    this.settings = settings;
	}
	
	public Sparrow() {
	    this(new SparrowSettings());
	}
	
	@Override
	public int selectLiteralToFlip(int[] candidates) {
	    time++;
	    double[] values = new double[candidates.length];
	    double totalValue = 0;
	    int i = 0;
	    
		for (int lit : candidates) {
		    int var = Math.abs(lit);
		    int score = 0;
		    //score += clManager.computeMakecount(lit); 
		    score -= clManager.computeBreakCount(lit);
		    double age = ages[var] - time;
		    
		    double ps = Math.pow(settings.scoreBaseExp, score);
		    double pa = Math.pow((age / settings.ageDivisor), settings.ageExponent) + 1;
		    
		    values[i] = ps * pa;
		    i++;
		    totalValue += ps * pa;
		}
		double rand = random.nextDouble();
		for (int k = 0; k < i; k++) {
		    double d = values[k] / totalValue;
		    if (d > rand) {
		        return candidates[k];
		    } else {
		        rand -= d;
		    }
		}	
		return candidates[candidates.length - 1];
	}

}
